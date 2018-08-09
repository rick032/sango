package sango.spring.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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

import com.google.api.client.util.IOUtils;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.utils.SystemProperty.Environment.Value;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.ListItem;
import com.google.appengine.tools.cloudstorage.ListOptions;
import com.google.appengine.tools.cloudstorage.ListResult;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.common.io.ByteStreams;

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

	@PostConstruct
	public void init() {
		Value location = SystemProperty.environment.value();
		if (location == SystemProperty.Environment.Value.Production) {
			readGS();
		} else {
			readGS_Dev();
		}
	}

	private void readGS_Dev() {
		File initialFile = new File("src/main/resources/DEVICE_08_00_27_46_1A_FF");
		try {

			Device device = IOUtils.deserialize(ByteStreams.toByteArray(new FileInputStream(initialFile)));
			log.info(device.getGamename());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readGS() {

		ListResult list = null;
		try {
			list = gcsService.list(BUCKET_NAME, new ListOptions.Builder().setPrefix(PRFIX_DEVICE).build());

			while (list.hasNext()) {
				ListItem l = list.next();
				String name = l.getName();
				log.info("Item Name:" + name);
				GcsFilename fileName = new GcsFilename(BUCKET_NAME, name);
				GcsInputChannel readChannel = null;
				BufferedReader reader = null;
				try {
					readChannel = gcsService.openPrefetchingReadChannel(fileName, 0, BUFFER_SIZE);
					/*
					 * reader = new BufferedReader(Channels.newReader(readChannel,
					 * StandardCharsets.ISO_8859_1.name())); String line; while ((line =
					 * reader.readLine()) != null) { log.info("READ:" + line); }
					 */
					try (InputStream inputStream = Channels.newInputStream(readChannel)) {
						Device device = IOUtils.deserialize(ByteStreams.toByteArray(inputStream));
						if (deviceService.findByMacAddr(device.getMacAddr()) == null) {
							deviceService.save(device);
							log.info("SAVE:" + device.toString());
						}
					}
				} finally {
					if (readChannel != null) {
						readChannel.close();
					}
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			device.setStartTime(new Timestamp(System.currentTimeMillis()));
			deviceService.save(device);

			try {

				Value location = SystemProperty.environment.value();
				if (location == SystemProperty.Environment.Value.Production) {
					writeToGS(device);
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
	private final String PRFIX_DEVICE = "DEVICE_";

	private void writeToGS(Device device) throws IOException {
		GcsFileOptions instance = GcsFileOptions.getDefaultInstance();
		GcsFilename fileName = new GcsFilename(BUCKET_NAME, PRFIX_DEVICE + device.getMacAddr());
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, instance);
		// InputStream is = new ByteArrayInputStream(IOUtils.serialize(device));
		// copy(is, Channels.newOutputStream(outputChannel));
		outputChannel.write(ByteBuffer.wrap(IOUtils.serialize(device)));
		outputChannel.close();
	}

	/**
	 * Transfer the data from the inputStream to the outputStream. Then close both
	 * streams.
	 * 
	 * private void copy(InputStream input, OutputStream output) throws IOException
	 * { try { byte[] buffer = new byte[BUFFER_SIZE]; int bytesRead =
	 * input.read(buffer); while (bytesRead != -1) { output.write(buffer, 0,
	 * bytesRead); bytesRead = input.read(buffer); } } finally { input.close();
	 * output.close(); } }
	 */

	@RequestMapping(value = "/device/update", method = RequestMethod.POST)
	public String update(Model model, Device device, HttpServletRequest request) {
		// @RequestParam String imei, @RequestParam String macAddr, @RequestParam String
		// deviceId, @RequestParam String gamename, @RequestParam String username
		// log.info("IMEI:" + imei + ",macAddr:" + macAddr+ ",deviceId:" + deviceId+
		// ",username:" + username+ ",gamename:" + gamename);
		log.info(device.toString());
		String oid = request.getParameter("oid");
		Device device2 = deviceService.findByOid(oid);
		if (device2 != null) {
			device2.setDeviceID(device.getDeviceID());
			device2.setEnabled(device.isEnabled());
			device2.setEndTime(device.getEndTime());
			device2.setGamename(device.getGamename());
			device2.setImei(device.getImei());
			device2.setUsername(device.getUsername());
			device2.setMacAddr(device.getMacAddr());
			deviceService.update(device2);

		} else {
			// "該MAC不存在!";
		}
		return index(model, null, request);
	}
}
