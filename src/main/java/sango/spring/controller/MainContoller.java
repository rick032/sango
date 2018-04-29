package sango.spring.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import sango.spring.model.Device;
import sango.spring.service.DeviceService;

@Controller
public class MainContoller {
	private static final Logger log = Logger.getLogger(MainContoller.class);
	@Autowired
	DeviceService deviceService;

	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		// model.addAttribute("message", "You are logged in as " + principal.getName());
		// return "index";
		List<Device> devices = deviceService.findAll();
		model.addAttribute("devices", devices);
		model.addAttribute("Device", new Device());
		return "index";
	}
}
