package com.evstation.charge.station.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evstation.charge.station.dto.StationDto;
import com.evstation.charge.station.entity.StationInfo;

@Repository
public interface StationInfoRepository extends JpaRepository<StationInfo, String>{

	Page<StationInfo> findByStationAddrContains(Pageable pageable,String searchTxt);
}