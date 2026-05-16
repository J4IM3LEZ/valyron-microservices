package com.realmofvalyron.ms_misiones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsMisionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMisionesApplication.class, args);
	}

}