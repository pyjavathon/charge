package com.evstation.charge.station.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evstation.charge.station.entity.StationInfo;
import com.evstation.charge.station.repository.StationInfoRepository;
import com.evstation.charge.station.service.StationInfoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StationInfoServiceImpl implements StationInfoService {

	private final StationInfoRepository statInfoRepo;
	
	@Override
	public List<StationInfo> findAll() {
		return statInfoRepo.findAll();
	}

}
