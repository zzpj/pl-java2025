package com.zzpj.TrainTripsOrganizerService;

import com.zzpj.openapi.api.StationsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TrainTripsOrganizerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTripsOrganizerServiceApplication.class, args);
	}


	@Bean
	public StationsApi stationsApi() {
		return new StationsApi();
	}

	@Bean
	public CommandLineRunner commandLineRunner(StationsApi stationsApi) {
		return args -> {
			stationsApi.getStations().forEach(System.out::println);
		};
	}

	@Bean
	public ServiceInstanceListSupplier serviceInstanceListSupplier() {
		return new TrainTripsManagerSupplier("train-trips-service");
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}

@RestController
class TrainTripsOrganizerController {
	private final RestTemplate restTemplate;

	@Value("${spring.application.name}")
	private String applicationName;

	TrainTripsOrganizerController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/info")
	public String getHello() {
		ResponseEntity<String> forEntity = restTemplate.getForEntity("http://train-trips-service/hello/" + applicationName, String.class);
		return forEntity.getBody();
	}

	@Value("${config.server.demo}")
	private String message;

	@GetMapping("/getMessage")
	public String getMessage() {
		return "Property value: " + message;
	}
}