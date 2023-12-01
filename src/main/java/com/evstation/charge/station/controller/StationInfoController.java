package com.evstation.charge.station.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.evstation.charge.station.entity.StationInfo;
import com.evstation.charge.station.service.StationInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequiredArgsConstructor
@Controller
public class StationInfoController {
	
	private final StationInfoService stationInfoSer;
	
	@GetMapping("/")
	public String dots(Model m) {
		List<StationInfo> list = stationInfoSer.findAll();
		m.addAttribute("list", list);
		return "ev-station/station-info";
	}
	

}
