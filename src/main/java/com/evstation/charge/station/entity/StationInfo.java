package com.evstation.charge.station.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class StationInfo {

	@Id
	public String stationId;
	
	public String stationNm;
	
	public String stationAddr;
	
	public double stationLat;
	
	public double stationLng;
}