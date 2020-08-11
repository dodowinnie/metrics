package com.brandon.metrics.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MetricsGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetricsGatewayApplication.class, args);
	}

}
