package tk.tutorial.security.requestvuln.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {

	// A5: Broken Access Control
	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public String getViewAdminPage() {
		return "adminpage";
	}
}
