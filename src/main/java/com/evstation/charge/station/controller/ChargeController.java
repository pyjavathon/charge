package com.evstation.charge.station.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.evstation.charge.station.entity.Charge;
import com.evstation.charge.station.entity.ChargerMapping;
import com.evstation.charge.station.service.ChargeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping("/evstation")
@RequiredArgsConstructor
@Controller
public class ChargeController {
	
	private final ChargeService chargeSer;
	
	@GetMapping("/dots")
	public String dots(Model m) {
		List<ChargerMapping> dots = chargeSer.findTest();
		log.info("데이터 갯수: {}", dots.size());
		m.addAttribute("dots", dots);
		return "ev-station/test";
	}
	

}
