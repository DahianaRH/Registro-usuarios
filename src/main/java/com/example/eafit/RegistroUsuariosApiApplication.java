package com.example.eafit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.eafit"})
public class RegistroUsuariosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistroUsuariosApiApplication.class, args);
	}
}
