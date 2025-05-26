# Microservices 2025 - secure your microservices and authorize your users

## Spring Security

### Create simple service `TrainTripsUserService` to maintain users of our app
1. Open [Spring Initializr website](https://start.spring.io/)
1. Complete Metadata section: set Artifact name as `TrainTripsUserService`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata Group: com.zzpj
    * Artifact: TrainTripsUserService
    * Name: TrainTripsUserService
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.TrainTripsUserService
    * Packaging: Jar
    * Java: 21
    * Dependencies: Spring Web, Eureka Discovery Client, Spring Security, lombok
1. Click Generate button, download and unzip package
1. Copy unzipped `TrainTripsUserService` folder into your project folder
1. (Optional if using Eureka) add following annotations: `@EnableDiscoveryClient` in main class
1. Add (if using exising service without Spring Security) or check following dependency:
   ```yaml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   ```
1. Add some properties into `application.properties`
   ```properties
   spring.application.name=TrainTripsUserService
   server.port=8050
   eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
   ```


### Create the simplest secured app that will require authentication to access any point of service (variant 1A)
1. Add following annotation to main class: `@EnableWebSecurity` 
1. Add new controller:
   ```java
   @RestController
   @RequiredArgsConstructor
   class UserController {
   
       @GetMapping("/internal")
       public String getInternalMessage() {
           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
           User user = (User) auth.getPrincipal();
           return String.format("Hello %s!", user.getUsername());
       }
   }
   ```
1. Add into `application.properties`
   ```properties
   spring.security.user.name=admin
   spring.security.user.password=admin123
   ```
1. Test using browser: `http://localhost:8050/internal`
2. Test using by creating http entity:
   ```http request
   GET http://localhost:8050/internal
   Authorization: Basic admin admin123
   ```

### Create advanced setup that will require authentication to access specified point of service (variant 1B)
1. Comment user's login and password from `application.properties`
2. Add new _external_ endpoint implementation to `UserController`
   ```java
       @GetMapping("/external")
       public String getExternalMessage() {
           return "Hello all viewers!";
       }
   ```
3. Add `SecurityConfig` implentation:
   ```java
   @Configuration
   class SecurityConfig {
   
       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .authorizeHttpRequests(request -> request
                           .requestMatchers("/external").permitAll()
                           .anyRequest().authenticated()
                   )
                   .userDetailsService(userDetailsService())
                   .formLogin(e -> e.defaultSuccessUrl("/internal", true))
                   .build();
       }
   
       @Bean
       public UserDetailsService userDetailsService() {
           UserDetails user = User.withUsername("admin")
                   .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin123"))
                   .roles("ADMIN")
                   .build();
           return new InMemoryUserDetailsManager(user);
       }
   }
   ```
1. Test using browser the external url: `http://localhost:8050/external`
1. Test using browser previous, internal url: `http://localhost:8050/internal`

### JWT (JSON Web Token)
> otwarty standard, definiujący sposób wymiany danych między określonymi stronami za pośrednictwem dokumentów JSON
1. [Documentation how-to](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)
