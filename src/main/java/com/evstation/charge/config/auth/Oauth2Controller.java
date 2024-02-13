package com.evstation.charge.config.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Oauth2Controller {

	@GetMapping("/loginForm")
	public String home() {
		return "login-form";
	}
	
	@GetMapping("/private")
	public String privatePage() {
		return "private-page";
	}
	
	@GetMapping("/admin")
	public String adminPage() {
		return "admin-page";
	}
}
