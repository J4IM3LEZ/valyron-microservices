package com.realmofvalyron.ms_gremios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsGremiosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGremiosApplication.class, args);
	}

}