package com.klashatask.countryapi;

import com.klashatask.countryapi.remote.ApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class CountryapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountryapiApplication.class, args);
	}

//	@Bean
//	public WebClient.Builder getWebClientBuilder(){
//		return WebClient.builder();
//	}

}
