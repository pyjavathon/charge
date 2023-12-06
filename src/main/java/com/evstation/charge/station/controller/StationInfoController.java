package com.evstation.charge.station.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.evstation.charge.config.auth.LoginUser;
import com.evstation.charge.config.auth.User;
import com.evstation.charge.station.entity.StationInfo;
import com.evstation.charge.station.service.StationInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StationInfoController {

	private final StationInfoService stationInfoSer;
	private final HttpSession httpSession;
	@GetMapping("/")
	public String dots(Model m,@LoginUser User user) {
		List<StationInfo> list = stationInfoSer.findAll();
		m.addAttribute("list", list);
		if (user != null) {
			m.addAttribute("username", user.getUsername());
		}

		return "index";
	}

}
