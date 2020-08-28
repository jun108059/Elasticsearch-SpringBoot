package com.searchengine.yjpark.api.server;

import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class DefaultRestApi {

	@GetMapping(value = "/welcome")
	public String welcome() {
		return "Welcome to Spring Boot";
	}
	
	@GetMapping(value = "/time")
	public String time() {
		return LocalTime.now().toString();
	}

}

@Controller
class WelcomeController {
	
	@RequestMapping("/index")
	public String locale() {
		return "index";
	}
}

@Controller
class signupController {
	
	@RequestMapping("/signup")
	public String locale() {
		return "signup";
	}
}

@Controller
class signResultController {
	
	@RequestMapping("/signup-result")
	public String locale() {
		return "signup-result";
	}
}

@Controller
class registrationController {
	
	@RequestMapping("/registration")
	public String locale() {
		return "registration";
	}
}