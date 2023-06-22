package com.app.avansserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class AvansServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AvansServerApplication.class, args);
		System.out.println(1);
	}

}
