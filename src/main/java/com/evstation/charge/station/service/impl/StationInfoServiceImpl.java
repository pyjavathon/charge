package com.evstation.charge.station.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.evstation.charge.station.dto.StationDto;
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

	@Override
	public Page<StationDto> search(Pageable pageable,String searchTxt) {
		
		Page<StationInfo> pInfos = statInfoRepo.findByStationAddrContains(pageable,searchTxt);
		List<StationDto> infos = new ArrayList<>();
		for(StationInfo info: pInfos) {
			StationDto result = StationDto.builder()
										  .info(info)
										  .build();
			infos.add(result);
		}
		return new PageImpl<>(infos,pageable,pInfos.getTotalElements());
	}

}
