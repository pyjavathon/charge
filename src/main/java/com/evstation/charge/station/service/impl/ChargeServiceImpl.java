package com.evstation.charge.station.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evstation.charge.station.entity.Charge;
import com.evstation.charge.station.entity.ChargerMapping;
import com.evstation.charge.station.repository.ChargeRepository;
import com.evstation.charge.station.service.ChargeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChargeServiceImpl implements ChargeService {

	private final ChargeRepository chargeRepo;

	@Override
	public List<ChargerMapping> findTest() {
		
		return chargeRepo.findTest();
	}
}
