package com.enterprise.enterprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class EnterpriseApplication {

	public static void main(String[] args) {
		// comment
		SpringApplication.run(EnterpriseApplication.class, args);
	}

	@RequestMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
	  return String.format("Hello %s!", name);
	}
}
