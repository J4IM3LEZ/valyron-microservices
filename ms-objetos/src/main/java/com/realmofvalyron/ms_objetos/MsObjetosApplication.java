package com.realmofvalyron.ms_objetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsObjetosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsObjetosApplication.class, args);
	}

}