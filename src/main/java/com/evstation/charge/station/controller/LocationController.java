package com.evstation.charge.station.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evstation.charge.station.entity.StationInfo;
import com.evstation.charge.station.repository.StationInfoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("public")
@RequiredArgsConstructor
public class LocationController {
	
	private final StationInfoRepository stationInfoRepo;
	
	@GetMapping("data")
	public void loadJsonFromApi(){
		String result = "";
		try {
			URL url = new URL("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo?serviceKey=eMsPQbhqWRabmm%2FOw%2BtJAkYxC9HjXqn3WTEvtE9IrHnc6pLMBeuZhWF1X6nywWRDbISm4PeBzFhM5yek6u6BOg%3D%3D&numOfRows=254613&pageNo=2&dataType=JSON");
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Content-type", "application/json");
			
			BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			result = bf.readLine();
			
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
			JSONObject obj = (JSONObject)jsonObject.get("items");
			
			JSONArray arr = (JSONArray)obj.get("item");
			
			for(int i = 0; i< arr.size();i++) {
				JSONObject tmp = (JSONObject)arr.get(i);
				String test = (String)tmp.get("statId");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
