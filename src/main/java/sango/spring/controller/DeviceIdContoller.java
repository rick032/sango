package sango.spring.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/device")
	public String index(Model model, Principal principal) {
		// model.addAttribute("message", "You are logged in as " + principal.getName());
		// return "index";
		List<Device> devices = deviceService.findAll();
		model.addAttribute("devices", devices);
		model.addAttribute("Device", new Device());
		log.info("device Count:" + devices.size());
		return "device";
	}

	@RequestMapping(value = "/device/add", method = RequestMethod.POST)	
	public String add(Model model, Device device) {
		// @RequestParam String imei, @RequestParam String macAddr, @RequestParam String
		// deviceId, @RequestParam String gamename, @RequestParam String username
		// log.info("IMEI:" + imei + ",macAddr:" + macAddr+ ",deviceId:" + deviceId+
		// ",username:" + username+ ",gamename:" + gamename);
		log.info(device.toString());
		Device device2 = deviceService.findByMacAddr(device.getMacAddr());
		if (device2 == null) {
			deviceService.save(device);
		}

		List<Device> devices = deviceService.findAll();
		model.addAttribute("devices", devices);
		model.addAttribute("Device", new Device());
		return "index";
	}

	@RequestMapping(value = "/device/update", method = RequestMethod.POST)	
	public ModelAndView update(Model model, Device device) {
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
		return new ModelAndView("index");
	}
}
