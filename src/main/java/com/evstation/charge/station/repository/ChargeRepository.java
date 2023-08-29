package com.evstation.charge.station.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evstation.charge.station.entity.Charge;
import com.evstation.charge.station.entity.ChargePK;
import com.evstation.charge.station.entity.ChargerMapping;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, ChargePK>{

	@Query(value="select c.lat as lat, c.lon as lon, c.stat_nm as statNm from charge c where c.chger_id = 1" , nativeQuery = true)
	List<ChargerMapping> findTest();
	
}
