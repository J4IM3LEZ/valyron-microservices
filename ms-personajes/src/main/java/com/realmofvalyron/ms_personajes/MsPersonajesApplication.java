package com.realmofvalyron.ms_personajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPersonajesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPersonajesApplication.class, args);
	}

}
