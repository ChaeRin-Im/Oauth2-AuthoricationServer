package com.example.oauth2authoricationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Oauth2authoricationserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2authoricationserverApplication.class, args);
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		System.out.println("pass: " + passwordEncoder.encode("pass"));
//		System.out.println("bar: " + passwordEncoder.encode("bar"));
	}

}
