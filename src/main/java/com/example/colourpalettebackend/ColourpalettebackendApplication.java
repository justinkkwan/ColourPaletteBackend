package com.example.colourpalettebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ColourpalettebackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColourpalettebackendApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/generate-colour-palette")
						.allowedOrigins("http://images.jkcodes.dev", "https://images.jkcodes.dev", "http://localhost:5173")
						.allowedMethods("POST", "OPTIONS")
						.allowedHeaders("*");

				registry.addMapping("/generate-outline")
						.allowedOrigins("http://images.jkcodes.dev", "https://images.jkcodes.dev", "http://localhost:5173")
						.allowedMethods("POST", "OPTIONS")
						.allowedHeaders("*");
			}
		};
	}
}
