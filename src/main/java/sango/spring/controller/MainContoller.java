package sango.spring.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainContoller {
	// private static final Logger log = Logger.getLogger(MainContoller.class);

	@RequestMapping("/")
	public String index(Model model, Principal principal) {
		// model.addAttribute("message", "You are logged in as " + principal.getName());
		// return "index";
		// List<Device> devices = deviceService.findAll();
		// model.addAttribute("devices", devices);
		// model.addAttribute("Device", new Device());
		// log.info("device Count:" + devices.size());
		return "index";
	}
}
