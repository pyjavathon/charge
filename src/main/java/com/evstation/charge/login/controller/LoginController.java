package com.evstation.charge.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.evstation.charge.login.entity.UserEntity;
import com.evstation.charge.login.service.LoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class LoginController {
	
	private final LoginService loginSer;
	
	@GetMapping
	public String joinForm(Model m) {
		m.addAttribute("user", new UserEntity());
		return "login/join";
	}
	
	@PostMapping
	public String joinUser(@ModelAttribute UserEntity joinUser) {
		UserEntity saveUser = loginSer.save(joinUser);
		return "login/login";
	}

}
