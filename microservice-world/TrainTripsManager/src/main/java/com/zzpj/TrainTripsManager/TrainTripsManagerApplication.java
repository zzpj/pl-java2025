package com.zzpj.TrainTripsManager;

import com.zzpj.openapi.api.StationsApi;
import com.zzpj.openapi.model.Station;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@EnableDiscoveryClient
@SpringBootApplication
public class TrainTripsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTripsManagerApplication.class, args);
    }

}

@RestController
class TestController {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${server.port}")
    private String appPort;

    @GetMapping("/hello/{name}")
    public String getServiceName(@PathVariable("name") String name) {
        return "Hello " + name + ", you are using " + applicationName + " on port: " + appPort;
    }
}


@RestController
@RequiredArgsConstructor
class StationController implements StationsApi {

    private final StationService stationService;

    @Override
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getStations());
    }
}

@Service
class StationService {
    public List<Station> getStations() {
        Station plStation = new Station().id(UUIDConstant.LODZ_STATION).name("Łódź Fabryczna").countryCode("PL").timezone("Europe/Warsaw");
        Station deStation = new Station().id(UUIDConstant.BERLIN_STATION).name("Berlin Hauptbahnhof").countryCode("DE").timezone("Europe/Berlin");
        Station frStation = new Station().id(UUIDConstant.PARIS_STATION).name("Paris Gare du Nord").countryCode("FR").timezone("Europe/Paris");
        Station itStation = new Station().id(UUIDConstant.ROME_STATION).name("Roma Termini").countryCode("IT").timezone("Europe/Rome");
        return List.of(plStation, deStation, frStation, itStation);
    }
}

@UtilityClass
class UUIDConstant {
    public static final UUID LODZ_STATION = UUID.fromString("b2cc2fe2-be4b-4733-9e21-9419711d0e04");
    public static final UUID PARIS_STATION = UUID.fromString("083d3f87-a738-4567-9472-2cf0c325c115");
    public static final UUID BERLIN_STATION = UUID.fromString("23c20c5f-d257-46f6-ace3-9074dad470a2");
    public static final UUID ROME_STATION = UUID.fromString("139d47ee-4724-4028-b261-e003fe5fcc40");
    public static final UUID BOOKING_UUID = UUID.fromString("29e1ca47-7989-4e4c-8c93-525e8f68ae71");
    public static final UUID TRIP_LODZ_BERLIN_UUID = UUID.fromString("ec60282f-24b7-4b60-b209-78c57f475112");
    public static final UUID TRIP_BERLIN_PARIS_UUID = UUID.fromString("5fddbeae-f1ba-497d-927c-49c9b9e6abd0");
    public static final UUID TRIP_PARIS_ROME_UUID = UUID.fromString("23f2c16b-8e35-4960-b8f4-bc18e80fe943");
    public static final UUID TRIP_ROME_LODZ_UUID = UUID.fromString("1231765e-360a-4eff-96e6-bfba39606c34");
}