package com.projects;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Airbnb App API",
				version = "1.0",
				description = "API documentation for Airbnb-style app",
				contact = @Contact(name = "Aman T", email = "you@example.com")
		)
)
public class AirBnbAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirBnbAppApplication.class, args);
	}

}
