package com.kh.spring.busan.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class BusanServiceImpl implements BusanService {

	private final String SERVICE_KEY = "c5trzaPwjVSNZMt4HI4XMT1vK0MZvKmBAA6n74FmKEnxII8hLNdUhXV%2FvzJS6jaPObb4xTj0lnzTV5prBn6UIQ%3D%3D";
	
	@Override
	public String getBusans(int pageNo) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://apis.data.go.kr/6260000/FoodService/getFoodKr");
		sb.append("?ServiceKey=" + SERVICE_KEY);
		sb.append("&resultType=json");
		sb.append("&numOfRows=9");
		sb.append("&pageNo=" + pageNo);
		
		URI uri = null;
		
		try {
			uri = new URI(sb.toString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		String apiResponseData = new RestTemplate().getForObject(uri, String.class);
		
		log.info("{}", apiResponseData);
		return apiResponseData;
	}

	@Override
	public String requestGetBusanDetail(int pk) {
		// TODO Auto-generated method stub
		return null;
	}

}
