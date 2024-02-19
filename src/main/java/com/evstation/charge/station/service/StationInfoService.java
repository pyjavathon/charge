package com.evstation.charge.station.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evstation.charge.station.dto.StationDto;
import com.evstation.charge.station.entity.StationInfo;

public interface StationInfoService {

	List<StationInfo> findAll();

	Page<StationDto> search(Pageable pageable,String searchTxt);
	


}
