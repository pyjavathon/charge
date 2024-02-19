package com.evstation.charge.station.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.evstation.charge.config.auth.LoginUser;
import com.evstation.charge.config.auth.User;
import com.evstation.charge.config.auth.dto.SessionUser;
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
	public String dots(Model m,@LoginUser SessionUser user) {
		List<StationInfo> list = stationInfoSer.findAll();
		m.addAttribute("list", list);
		if (user != null) {
			m.addAttribute("username", user.getUsername());
		}

		return "index";
	}
	
	@GetMapping("search")
	public String search(Model m,@PageableDefault(page = 0, size = 10, sort = "stationId",direction = Sort.Direction.DESC)Pageable pageable,@RequestParam(name = "searchTxt") String searchTxt) {
		
		m.addAttribute("sList", stationInfoSer.search(pageable,searchTxt));
		m.addAttribute("searchTxt", searchTxt);
		return "ev-station/search-list";
	}
	

}
