package com.searchengine.yjpark.api.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@RequestMapping(value = "/api")
public class BulkApi {

	@GetMapping(value = "/welcome")
	public String welcome() {
		return "Welcome to Spring Boot";
	}
	
	@GetMapping(value = "/time")
	public String time() {
		return LocalTime.now().toString();
	}

}