package com.northwind.shippingservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class ShippingserviceApplication {
	public static final String topicExchangeName = "spring-boot-exchange";

		public static void main(String[] args) {
		SpringApplication.run(ShippingserviceApplication.class, args);
	}

}
