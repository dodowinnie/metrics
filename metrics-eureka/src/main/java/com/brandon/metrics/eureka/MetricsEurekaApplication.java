package com.brandon.metrics.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MetricsEurekaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetricsEurekaApplication.class, args);
		System.out.println("564646");
		System.out.println("本地开发555566666");
		

	}
}
