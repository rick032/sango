package sango.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.utils.SystemProperty.Environment.Value;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;

import sango.spring.model.Device;
import sango.spring.model.DeviceLog;
import sango.spring.service.DeviceLogService;
import sango.spring.service.DeviceService;

@Controller
public class DeviceIdContoller {
	private static final Logger log = Logger.getLogger(DeviceIdContoller.class);

	@Autowired
	DeviceService deviceService;

	@Autowired
	DeviceLogService deviceLogService;

	final String 驗證成功 = "Y";
	final String 認證不存在 = "N";
	final String 啟動時間未到 = "1";
	final String 認證已過期 = "2";
	final String 認證未啟用 = "3";

	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public String check(@RequestParam String imei, @RequestParam String macAddr, @RequestParam String deviceId) {
		log.info("IMEI:" + imei + "macAddr:" + macAddr);

		Device device = deviceService.findByMacAddr(macAddr);
		String result = 驗證成功;
		if (device != null) {
			Timestamp nowTime = new Timestamp(System.currentTimeMillis());// Utils.getTWTimestamp();
			log.info(device.toString());
			if (nowTime.before(device.getStartTime())) {
				result = 啟動時間未到;
			} else if (nowTime.after(device.getEndTime())) {
				result = 認證已過期;
			} else if (!device.isEnabled()) {
				result = 認證未啟用;
			}
			deviceLogService.save(new DeviceLog(device, result));
		} else {
			result = 認證不存在;			
		}		
		return result;
	}

	@RequestMapping("/device")
	public String index(Model model, Principal principal, HttpServletRequest request) {
		// model.addAttribute("message", "You are logged in as " + principal.getName());
		// return "index";
		List<Device> devices = deviceService.findAll();
		Object macAddr = request.getParameter("macAddr");
		List<DeviceLog> deviceLogs = new ArrayList<DeviceLog>();
		if (!StringUtils.isEmpty(macAddr)) {
			deviceLogs = deviceLogService.findByMacAddr((String) macAddr);
		}
		model.addAttribute("deviceLogs", deviceLogs);
		model.addAttribute("devices", devices);
		model.addAttribute("Device", new Device());
		log.info("device Count:" + devices.size());
		return "device";
	}

	private String INSERT_DEVICE = "insert into DEVICE(MACADDR,IMEI,deviceId,gameName,userName,enabled,startTime,endTime) values(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',{5},CURRENT_TIMESTAMP,''{6}'');";

	// for
	// test:http://localhost:8080/check?macAddr=08:00:27:72:E8:A1&imei=359090150142188&deviceId=14dae9e9352e2959
	@RequestMapping(value = "/device/add", method = RequestMethod.POST)
	public String add(Model model, Device device, HttpServletRequest request) {
		// @RequestParam String imei, @RequestParam String macAddr, @RequestParam String
		// deviceId, @RequestParam String gamename, @RequestParam String username
		// log.info("IMEI:" + imei + ",macAddr:" + macAddr+ ",deviceId:" + deviceId+
		// ",username:" + username+ ",gamename:" + gamename);
		log.info(device.toString());
		Device device2 = deviceService.findByMacAddr(device.getMacAddr());
		if (device2 == null) {
			deviceService.save(device);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS.sss");
			String insertSQL = MessageFormat.format(INSERT_DEVICE,
					new Object[] { device.getMacAddr(), device.getImei(), device.getDeviceID(), device.getGamename(),
							device.getUsername(), String.valueOf(device.isEnabled()),
							sdf.format(device.getEndTime()) });
			log.info("@@@INSERT SQL:" + insertSQL);
			try {

				Value location = SystemProperty.environment.value();
				if (location == SystemProperty.Environment.Value.Production) {
					writeToGS(insertSQL);
				} else if (location == SystemProperty.Environment.Value.Development) {

				}

			} catch (IOException e) {
				log.error("Write to GS fail", e.getCause());
			}
		}

		return index(model, null, request);
	}

	/**
	 * This is where backoff parameters are configured. Here it is aggressively
	 * retrying with backoff, up to 10 times but taking no more that 15 seconds
	 * total to do so.
	 */
	private final GcsService gcsService = GcsServiceFactory.createGcsService(new RetryParams.Builder()
			.initialRetryDelayMillis(10).retryMaxAttempts(10).totalRetryPeriodMillis(15000).build());

	/**
	 * Used below to determine the size of chucks to read in. Should be > 1kb and <
	 * 10MB
	 */
	private static final int BUFFER_SIZE = 2 * 1024 * 1024;
	private static final String BUCKET_NAME = "staging.weighty-site-140903.appspot.com";

	private void writeToGS(String sql) throws IOException {
		GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
		GcsFilename fileName = new GcsFilename(BUCKET_NAME, "start.sql");
		GcsOutputChannel outputChannel;
		outputChannel = gcsService.createOrReplace(fileName, instance);
		InputStream is = new ByteArrayInputStream(sql.getBytes(StandardCharsets.UTF_8));

		copy(is, Channels.newOutputStream(outputChannel));

	}

	/**
	 * Transfer the data from the inputStream to the outputStream. Then close both
	 * streams.
	 */
	private void copy(InputStream input, OutputStream output) throws IOException {
		try {
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = input.read(buffer);
			while (bytesRead != -1) {
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}
		} finally {
			input.close();
			output.close();
		}
	}

	@RequestMapping(value = "/device/update", method = RequestMethod.POST)
	public String update(Model model, Device device, HttpServletRequest request) {
		// @RequestParam String imei, @RequestParam String macAddr, @RequestParam String
		// deviceId, @RequestParam String gamename, @RequestParam String username
		// log.info("IMEI:" + imei + ",macAddr:" + macAddr+ ",deviceId:" + deviceId+
		// ",username:" + username+ ",gamename:" + gamename);
		log.info(device.toString());
		Device device2 = deviceService.findByMacAddr(device.getMacAddr());
		if (device2 != null) {
			deviceService.update(device);
		} else {
			// "該MAC不存在!";
		}
		return index(model, null, request);
	}
}
