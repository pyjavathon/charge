package com.evstation.charge.station.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Charge {
	
	@EmbeddedId
	private ChargePK pk;
	
	private String statNm;
	
	private Integer chargerType;
	
	private String addr;
	
	private BigDecimal lat;
	
	private BigDecimal lon;
	
	private String useTime;
	
	private String busiId;
	
	private String busiNm;
	
	private String busiCall;
	
	private Integer chargerStat;
	
	private String statUpdDt;
	
	private String powerType;
	
	private Integer cityDoCd;
	
	private String parkingFreeYn;
	
	private String statNote;
	
	private String limitYn;
	
	private String limitDetail;
	
	private String delYn;
	
	private String delDetail;
	
	

}
