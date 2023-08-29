package com.evstation.charge.station.entity;

import java.math.BigDecimal;


public interface ChargerMapping {
	
	Long getChgerId();

	String getStatNm();
	
	String getAddr();
	
	BigDecimal getLat();

	BigDecimal getLon();
}
