package sango.spring.controller;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainContoller {
	private static final Logger log = Logger.getLogger(MainContoller.class);

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
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	@ResponseBody
	public String check(Model model, Principal principal) {
		log.info("###############PING");
		String result = "ping success";
		
		return result;
	}
}
