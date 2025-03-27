package com.kh.spring.busan.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.busan.service.BusanService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@CrossOrigin(origins="http://localhost:5173")
@RequestMapping(value="busans", produces="application/json; charset=UTF-8")
@RequiredArgsConstructor
public class BusanController {
	
	private final BusanService busanService;
	
	
	@GetMapping	
	public ResponseEntity<String> getBusans(@RequestParam(name="pageNo", defaultValue="1") int pageNo) {
		
		// log.info("busanData: {}", busanService.getBusans(pageNo));
		
		String responseData = busanService.getBusans(pageNo);
		
		return ResponseEntity.ok(responseData);
	}
}
