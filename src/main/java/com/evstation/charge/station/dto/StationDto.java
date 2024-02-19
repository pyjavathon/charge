package com.evstation.charge.station.dto;

import com.evstation.charge.station.entity.StationInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StationDto {

	private String stationId;
	private String stationNm;
	private String stationAddr;
	
	@Builder
	public StationDto(StationInfo info) {
		this.stationId = info.getStationId();
		this.stationNm = info.getStationNm();
		this.stationAddr = info.getStationAddr();
	}
}
