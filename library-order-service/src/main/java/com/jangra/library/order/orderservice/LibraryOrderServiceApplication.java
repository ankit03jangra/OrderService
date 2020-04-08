package com.jangra.library.order.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.jangra.library.order.orderservice")
@EnableDiscoveryClient
public class LibraryOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryOrderServiceApplication.class, args);
	}

}
