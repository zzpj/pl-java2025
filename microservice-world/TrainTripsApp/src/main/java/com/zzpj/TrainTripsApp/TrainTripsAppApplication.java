package com.zzpj.TrainTripsApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TrainTripsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTripsAppApplication.class, args);
	}

}
