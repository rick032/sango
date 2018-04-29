package sango.spring.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sango.spring.model.Device;
import sango.spring.service.DeviceService;

@Controller
public class DeviceIdContoller {
	private static final Logger log = Logger.getLogger(DeviceIdContoller.class);

	@Autowired
	DeviceService deviceService;

	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ResponseBody
	public String check(@RequestParam String imei, @RequestParam String macAddr, @RequestParam String deviceId) {
		log.info("IMEI:" + imei + "macAddr:" + macAddr);
		Device device = deviceService.findByMacAddr(macAddr);
		if (device != null) {
			log.info(device.toString());
		}
		return device != null && device.isEnabled() ? "Y" : "N";
	}
	
	@RequestMapping(value = "/device/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(Device device) {
		//@RequestParam String imei, @RequestParam String macAddr, @RequestParam String deviceId, @RequestParam String gamename, @RequestParam String username
		//log.info("IMEI:" + imei + ",macAddr:" + macAddr+ ",deviceId:" + deviceId+ ",username:" + username+ ",gamename:" + gamename);
		log.info(device.toString());
		Device device2 = deviceService.findByMacAddr(device.getMacAddr());
		if (device2 != null) {
			return "已存在!";			
		}else {
			deviceService.save(device); 
			return "Y";
		}
		
	}
	
	@RequestMapping(value = "/device/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(Device device) {
		//@RequestParam String imei, @RequestParam String macAddr, @RequestParam String deviceId, @RequestParam String gamename, @RequestParam String username
		//log.info("IMEI:" + imei + ",macAddr:" + macAddr+ ",deviceId:" + deviceId+ ",username:" + username+ ",gamename:" + gamename);
		log.info(device.toString());
		Device device2 = deviceService.findByMacAddr(device.getMacAddr());
		if (device2 == null) {
			return "該MAC不存在!";			
		}else {
			deviceService.update(device); 
			return "Y";
		}
		
	}
}
