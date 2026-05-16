package com.realmofvalyron.ms_poderes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsPoderesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPoderesApplication.class, args);
	}

}