package com.kh.spring.busan.service;

public interface BusanService {
	
	// 1절
	String getBusans(int pageNo);
	
	String requestGetBusanDetail(int pk);
}
