package com.evstation.charge.station.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class ChargePK implements Serializable {
	
	private Long chargerId;
	
	private String statId;

}
