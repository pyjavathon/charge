package com.evstation.charge.station.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.evstation.charge.station.entity.Charge;
import com.evstation.charge.station.entity.ChargerMapping;

public interface ChargeService {
	
	List<ChargerMapping> findTest();

}
