package com.searchengine.yjpark.api.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class DefaultRestApi {

	@RequestMapping(value = "/welcome")
	public String welcome() {
		return "Welcome to Spring Boot";
	}

}