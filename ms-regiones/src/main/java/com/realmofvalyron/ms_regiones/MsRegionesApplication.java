package com.realmofvalyron.ms_regiones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsRegionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRegionesApplication.class, args);
	}

}