# Microservices 2025

## OpenAPI
> OpenAPI to standard pozwalający na definiowanie interfejsów API w sposób czytelny zarówno dla ludzi, jak i maszyn. 
Umożliwia automatyczne generowanie dokumentacji, kodu klienta i serwera oraz testowanie API bez potrzeby ręcznego pisania specyfikacji. 
Dzięki niemu programiści mogą łatwiej projektować, udostępniać i integrować usługi między różnymi systemami.

* [OpenAPI strona główna](https://www.openapis.org/)
* [OpenAPI ready examples](https://nordicapis.com/3-example-openapi-definitions/)
* [OpenAPI file (train-api)](train-api-example.yml)

### OpenAPI in practise (server)
* From `start.spring.io`:
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata 
      * Group: com.zzpj
      * Artifact: TrainTripsManager
      * Name: TrainTripsManager
      * Description: Demo project for Spring Boot
      * Package name: com.zzpj.TrainTripsManager
      * Packaging: Jar
      * Java: 21
    * Dependencies: Web, Actuator, Lombok

* Dependencies-config:
    * dependencies:
  ```xml
    <dependency>
        <groupId>org.openapitools</groupId>
        <artifactId>jackson-databind-nullable</artifactId>
        <version>0.2.6</version>
    </dependency>
  ```
  ```xml
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.8.8</version>
    </dependency>
  ```
    * plugin:
  ```xml
    <plugin>
        <groupId>org.openapitools</groupId>
        <artifactId>openapi-generator-maven-plugin</artifactId>
        <version>7.13.0</version>
        <executions>
            <execution>
                <goals>
                    <goal>generate</goal>
                </goals>
                <configuration>
                    <inputSpec>${project.basedir}/api/train-api.yml</inputSpec>
                    <generatorName>spring</generatorName>
                    <apiPackage>com.zzpj.openapi.api</apiPackage>
                    <modelPackage>com.zzpj.openapi.model</modelPackage>
                    <configOptions>
                        <interfaceOnly>true</interfaceOnly>
                        <useJakartaEe>true</useJakartaEe>
                        <sourceFolder>src/gen/java/main</sourceFolder>
                        <library>spring-cloud</library>
                    </configOptions>
                </configuration>
            </execution>
        </executions>
    </plugin>
  ```
* run `mvn clean install`
* implement generated api

```java

@RestController
@RequiredArgsConstructor
class StationController implements StationsApi {

    private final StationService stationService;

    @Override
    public ResponseEntity<List<Station>> getStations() {
        return ResponseEntity.ok(stationService.getStations());
    }
}
```

```java

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
```

```java
@RestController
@RequiredArgsConstructor
class TripsController implements TripsApi {

    private final TripService tripService;

    @Override
    public ResponseEntity<List<Trip>> getTrips(UUID destination, OffsetDateTime date, UUID origin, Boolean bicycles, Boolean dogs) {
        return ResponseEntity.ok(tripService.getTrips(origin, destination, date, bicycles, dogs));
    }
}

@Service
class TripService {
    public List<Trip> getTrips(UUID origin, UUID destination, OffsetDateTime date, Boolean bicycles, Boolean dogs) {
        List<Trip> trips = List.of(
                new Trip().id(UUIDConstant.TRIP_LODZ_BERLIN_UUID)
                        .arrivalTime(OffsetDateTime.now())
                        .departureTime(OffsetDateTime.now())
                        .price(BigDecimal.valueOf(30))
                        .bicyclesAllowed(true)
                        .dogsAllowed(true)
                        .operator("PKP")
                        .origin(UUIDConstant.LODZ_STATION.toString())
                        .destination(UUIDConstant.BOOKING_UUID.toString()),
                new Trip().id(UUIDConstant.TRIP_BERLIN_PARIS_UUID)
                        .arrivalTime(OffsetDateTime.now())
                        .departureTime(OffsetDateTime.now())
                        .price(BigDecimal.valueOf(60))
                        .bicyclesAllowed(true)
                        .dogsAllowed(false)
                        .operator("DB")
                        .origin(UUIDConstant.BERLIN_STATION.toString())
                        .destination(UUIDConstant.PARIS_STATION.toString()),
                new Trip().id(UUIDConstant.TRIP_PARIS_ROME_UUID)
                        .arrivalTime(OffsetDateTime.now())
                        .departureTime(OffsetDateTime.now())
                        .price(BigDecimal.valueOf(90))
                        .bicyclesAllowed(false)
                        .dogsAllowed(false)
                        .operator("SCNF")
                        .origin(UUIDConstant.PARIS_STATION.toString())
                        .destination(UUIDConstant.ROME_STATION.toString())
        );
        return trips.stream()
                .filter(e -> origin.toString().equals(e.getOrigin()) && destination.toString().equals(e.getDestination()))
                .filter(e -> Objects.nonNull(date) && (date.equals(e.getArrivalTime()) || date.equals(e.getDepartureTime())))
                .filter(e -> bicycles == e.getBicyclesAllowed())
                .filter(e -> dogs == e.getDogsAllowed())
                .toList();
    }
}
```

```java
@RestController
@RequiredArgsConstructor
class BookingController implements BookingsApi {

    private final BookingService bookingService;

    @Override
    public ResponseEntity<Booking> createBooking(Booking booking) {
        bookingService.createBooking(booking);
        return ResponseEntity.ok(booking);
    }

    @Override
    public ResponseEntity<BookingPayment> createBookingPayment(UUID bookingId, BookingPayment bookingPayment) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<Void> deleteBooking(UUID bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Booking> getBooking(UUID bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    @Override
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(bookingService.getBookings());
    }

}

@RestController
class BookingService {

    private List<Booking> bookings;

    public BookingService() {
        bookings = new ArrayList<>();
        bookings.add(new Booking()
                .id(UUIDConstant.BOOKING_UUID)
                .hasBicycle(true)
                .hasDog(true)
                .passengerName("Franek")
                .tripId(UUIDConstant.TRIP_ROME_LODZ_UUID)
        );
    }

    public void createBooking(Booking booking) {
        booking.setId(UUID.randomUUID());
        bookings.add(booking);
    }

    public Booking getBooking(UUID bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void deleteBooking(UUID bookingId) {
        bookings.remove(getBooking(bookingId));
    }
}
```

* run app (set `server.port=8020` in _application.properties_)
* use url: http://localhost:8020/swagger-ui/index.html
* Reminder: generated code is not commited to repo

### OpenAPI in practise (client/consumer)

* Create second service with use of `start.spring.io`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsOrganizerService
    * Name: TrainTripsOrganizerService
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsOrganizerService
    * Packaging: Jar
    * Java: 21
    * Dependencies: Web, Actuator, Lombok
* complete `pom.xml` with openAPI dependencies:

```xml

<dependency>
    <groupId>org.openapitools</groupId>
    <artifactId>jackson-databind-nullable</artifactId>
    <version>0.2.6</version>
</dependency>
```

```xml

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.8</version>
</dependency>
```

```xml
<plugin>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-generator-maven-plugin</artifactId>
    <version>7.13.0</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
            <configuration>
                <inputSpec>${project.basedir}/api/train-api.yml</inputSpec>
                <generatorName>java</generatorName>
                <apiPackage>com.zzpj.openapi.api</apiPackage>
                <modelPackage>com.zzpj.openapi.model</modelPackage>
                <generateApiTests>false</generateApiTests>
                <generateModelTests>false</generateModelTests>
                <configOptions>
                    <useJakartaEe>true</useJakartaEe>
                    <library>resttemplate</library>
                    <sourceFolder>src/gen/java/main</sourceFolder>
                </configOptions>
            </configuration>
        </execution>
    </executions>
</plugin>
```

* complete `train-api.yml` with server URL (do replacement)

```yaml
servers:
  - url: http://localhost:8020/
    description: local dev instance
```

* remove spring test units if needed
* run `mvn clean install`
* complete code

```java

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
}
```

* run and test

## Read & watch about microservices (theoretical part)

### How to start
* [https://microservices.io/](https://microservices.io/)
* [12factor.net](https://12factor.net/)

### Spring Cloud documentation
* https://spring.io/projects/spring-cloud

### Infographics
* [Róźne propozycje architektur](img/arch-styles.png)
* [Wzorce architektoniczne](img/arch-patterns.png)
* [Architektura mikroserwisowa](img/micro-arch.png)

## Eureka Server
* Create second service with use of `start.spring.io`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsApp
    * Name: TrainTripsApp
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsApp
    * Packaging: Jar
    * Java: 21
    * Dependencies: Web, Eureka Server
* Open main class with `@SpringBootApplication` annotation
* Use Spring Cloud’s `@EnableEurekaServer` to stand up a registry with which other applications can communicate. This is
  a regular Spring Boot application with one annotation added to enable the service registry.
* By default, the registry also tries to register itself, so you need to disable that behavior as well
  in  `application.properties` file.
   ```properties
   eureka.client.register-with-eureka=false
   eureka.client.fetch-registry=false
   ```
* Select the port which will be used by Eureka Server
   ```properties
   server.port=8761
   ```
* Enter URL: `http://localhost:8761/`


### Register both, newly created services

* Complete `pom.xml`:
  ```xml
  <spring-cloud.version>2024.0.1</spring-cloud.version>
  ```
  ```xml
  <dependency>
     <groupId>org.springframework.cloud</groupId>
     <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  </dependency>
  ```
  ```xml
  <dependencyManagement>
     <dependencies>
         <dependency>
             <groupId>org.springframework.cloud</groupId>
             <artifactId>spring-cloud-dependencies</artifactId>
             <version>${spring-cloud.version}</version>
             <type>pom</type>
             <scope>import</scope>
         </dependency>
     </dependencies>
  </dependencyManagement>
  ```
* Add annotation `@EnableDiscoveryClient` to main class
* Add some properties into `application.properties`
   ```properties
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
   eureka.client.fetch-registry=true
   eureka.client.register-with-eureka=true
   ```
* Run all services and determine if service has been registered in Eureka Discovery Server, either by
  entering `http://localhost:8761/` or using logs.

### Spring Cloud Client Load balancer
1. [Load balancer - grafika](img/load-balancer.jpg)
1. Stop running `TrainTripsManager` and comment `server.port` properties
1. Add `TestController`:
   ```java
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
   ```
1. Run two (or more) instances using Spring Boot Run Configuration, use Environment > VM Options for setting ports:
  * `-Dserver.port=8021`
  * `-Dserver.port=8022`
  * `-Dserver.port=8023`
1. Refresh Eureka Discovery page and determine if both instances of the same service are available
1. Add load balancer dependency in `TrainTripsOrganizerService` project
   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-loadbalancer</artifactId>
   </dependency>
   ```
1. `TrainTripsManagerSupplier` implementation:
   ```java
    class TrainTripsManagerSupplier implements ServiceInstanceListSupplier {
    
        private final String serviceId;
    
        TrainTripsManagerSupplier(String serviceId) {
            this.serviceId = serviceId;
        }
    
        @Override
        public String getServiceId() {
            return serviceId;
        }
    
        @Override
        public Flux<List<ServiceInstance>> get() {
            ServiceInstance int1 = new DefaultServiceInstance("001", serviceId, "localhost", 8021, false);
            ServiceInstance int2 = new DefaultServiceInstance("002", serviceId, "localhost", 8022, false);
            ServiceInstance int3 = new DefaultServiceInstance("003", serviceId, "localhost", 8023, false);
            return Flux.just(List.of(int1, int2, int3));
        }
    }
   ```   
1. Create `restTemplate` and `ServiceInstanceListSupplier` beans:
   ```java
    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new TrainTripsManagerSupplier("train-trips-service");
    }
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
   ```

1. `TrainTripsOrganizerController` implementation:
   ```java
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
    }
   ```
1. Verify URL: `http://localhost:8030/info`
1. OpenAPI case by modifying `api.yaml`:
```yaml
servers:
  - url: http://localhost:{port}/
    description: local dev instance
    variables:
      port:
        default: '8021'
        enum:
          - '8021'
          - '8022'
          - '8023'
```
1. Run mvn:  `mvn clean install`
1. Modify `CommandLineRunner` bean:
    ```java
    @Bean
    @LoadBalanced
    public CommandLineRunner commandLineRunner(StationsApi stationsApi) {
        return args -> {
            System.out.println(stationsApi.getApiClient().getBasePath());
            stationsApi.getStations().forEach(System.out::println);
        };
    }    
    ```
1. Rerun for OpenAPI

## Config Server

1. Open [Spring Initializr website](https://start.spring.io/)
1. Complete Metadata section: set Artifact name as `TrainTripsConfigServer`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsConfigServer
    * Name: TrainTripsConfigServer
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsConfigServer
    * Packaging: Jar
    * Java: 21
    * Dependencies: Spring Web, Eureka Discovery Client, Config Server
1. Click Generate button, download and unzip package
1. Copy unzipped `TrainTripsConfigServer` folder into your project folder
1. Add following annotations: `@EnableDiscoveryClient` & `@EnableConfigServer` into main class
1. Add some properties into `application.properties`
   ```properties
    spring.application.name=TrainTripsConfigServer
    server.port=8040
    eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
    
    spring.cloud.config.server.git.uri=https://github.com/zzpj/demo-config-server.git
    spring.cloud.config.server.git.default-label=main
    spring.cloud.config.server.git.clone-on-start=true
   ```
1. Show Github repo in IntelliJ or browser: https://github.com/zzpj/demo-config-server.git
1. Complete `pom.xml` of `TrainTripsOrganizerService`
   ```xml
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-config</artifactId>
   </dependency>
   ```
1. Go to `application.properties` files and add:
   ```properties
    spring.config.import=optional:configserver:
    spring.cloud.config.discovery.enabled=true
    spring.cloud.config.discovery.service-id=TrainTripsConfigServer
    #spring.cloud.config.name=${spring.application.name}
    spring.cloud.config.name=train-trips-organizer-service
    spring.cloud.config.profile=dev
    spring.cloud.config.label=main
    spring.cloud.config.fail-fast=true
   ```
1. Add properties check with use of `@Value` annotation
   ```java
    @Value("${config.server.demo}")
    private String message;
    
    @GetMapping("/getMessage")
    public String getMessage() {
        return "Property value: " + message;
    }
   ```
1. Remember about following properties naming rules
   ```
   /{application}/{profile}[/{label}]
   /{application}-{profile}.yml
   /{label}/{application}-{profile}.yml
   /{application}-{profile}.properties
   /{label}/{application}-{profile}.properties
   ```

