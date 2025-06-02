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
3. Add `SecurityConfig` implementation:
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
2. Or more sophisticated:
   ```http request
   POST http://localhost:8050/login
   Content-Type: application/x-www-form-urlencoded
   
   username = admin & password = admin123
   ```

### JWT (JSON Web Token)

> otwarty standard, definiujący sposób wymiany danych między określonymi stronami za pośrednictwem dokumentów JSON

1. Add following dependencies
   ```xml
     <dependency>
         <groupId>org.springframework.security</groupId>
         <artifactId>spring-security-oauth2-jose</artifactId>
     </dependency>
     <dependency>
         <groupId>org.springframework.security</groupId>
         <artifactId>spring-security-oauth2-resource-server</artifactId>
     </dependency>
   ```

4. Modify `SecurityConfig` class:
   ```java
   @Configuration
   class SecurityConfig {
   
       private static final String SECRET = "pb5uKLB7hWHBBMxqYLjr4-gB4zdgxEkFQJgdwV2TVf7RiANXYcrcH4inE_RjWTlv2Ddppybp7gIDuyIRV";
   
       @Bean
       public JwtEncoder jwtEncoder() {
           SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
           return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
       }
   
       @Bean
       public JwtDecoder jwtDecoder() {
           SecretKeySpec secretKey = new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
           return NimbusJwtDecoder
                   .withSecretKey(secretKey)
                   .macAlgorithm(MacAlgorithm.HS256)
                   .build();
       }
   
       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           return http
                   .csrf(AbstractHttpConfigurer::disable)
                   .authorizeHttpRequests(request ->
                           request
                                   .requestMatchers("/external", "/token").permitAll()
                                   .requestMatchers("/validate").authenticated()
                   )
                   .userDetailsService(userDetailsService())
                   .formLogin(e -> e.defaultSuccessUrl("/internal", true)) // COMMENTED
                   .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                   .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter())))
                   .build();
       }
   
       @Bean
       public UserDetailsService userDetailsService() {
           UserDetails user = User
                   .withUsername("admin")
                   .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin123"))
                   .roles("ADMIN")
                   .build();
           return new InMemoryUserDetailsManager(user);
       }
   
   }
   ```
3. Complete `UserController` class:
   ```java
   @PostMapping("/token")
   public ResponseEntity<String> token(@RequestBody AuthRequest request) {
   
      //TODO: add credentials checks
   
      JwtClaimsSet claims = JwtClaimsSet.builder()
              .subject(request.getUsername())
              .issuer("user_app")
              .issuedAt(Instant.now())
              .expiresAt(Instant.now().plusSeconds(120))
              .build();
   
      JwsHeader jwsHeader = JwsHeader.with(() -> "HS256").build();
      String tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
      log.info("tokenValue: {}", tokenValue);
      return ResponseEntity.ok(tokenValue);
   }
   
   @GetMapping("/validate")
   public ResponseEntity<String> validateToken(@AuthenticationPrincipal Jwt jwt) {
      return ResponseEntity.ok("Token correct for user: " + jwt.getSubject());
   }
   ```
4. Add model:
   ```java
   @Data
   class AuthRequest {
       private String username;
       private String password;
   }
   ```

5. Test:
   ```http request
   POST http://localhost:8050/token
   Content-Type: application/json
   
   {
     "username": "admin",
     "password": "admin123"
   }
   
   > {%
       client.global.set("jwt_token", response.body);
   %}
   
   ###
   GET http://localhost:8050/validate
   Authorization: Bearer {{jwt_token}}
   ```

--TODO: moze wykorzystać `Authentication auth = SecurityContextHolder.getContext().getAuthentication();` zamiast
JSON? => *Jak starczy czasu...*

## Spring Authorization Server

### *Server side*

1. Open [Spring Initializr website](https://start.spring.io/)
1. Complete Metadata section: set Artifact name as `AuthorizationServer`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata Group: com.zzpj
    * Artifact: AuthorizationServer
    * Name: AuthorizationServer
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.AuthorizationServer
    * Packaging: Jar
    * Java: 21
    * Dependencies: Spring Web, Spring Security, Spring Authorization Server
1. Click Generate button, download and unzip package
1. Copy unzipped `AuthorizationServer` folder into your project folder
2. Provide config:
   ```java
   @Configuration
   @EnableWebSecurity
   class AuthorizationServerConfig {
   
       @Bean
       public RegisteredClientRepository registeredClientRepository() {
           RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                   .clientId("client")
                   .clientSecret("{noop}secret") // tylko do testów!
                   .redirectUri("http://localhost:8081/login/oauth2/code/client")
                   .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                   .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                   .scope(OidcScopes.OPENID)
                   .scope("profile")
                   .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                   .build();
   
           return new InMemoryRegisteredClientRepository(registeredClient);
       }
   
       @Bean
       public UserDetailsService users() {
           return new InMemoryUserDetailsManager(
                   User.withUsername("user1")
                           .password("{noop}password")
                           .authorities("ROLE_USER")
                           .passwordEncoder(password -> password) // no-op encoder
                           .build());
       }
   
       @Bean
       public AuthorizationServerSettings authorizationServerSettings() {
           return AuthorizationServerSettings.builder()
                   .issuer("http://auth.mydomain.com:8080")
                   .build();
       }
   }
   ```
6. Open in browser: `http://localhost:8080/.well-known/openid-configuration`

### *Client side*:

1. Open [Spring Initializr website](https://start.spring.io/)
1. Complete Metadata section: set Artifact name as `UserService`
    * Project: Maven
    * Language: Java
    * Spring Boot: 3.4.5
    * Project Metadata Group: com.zzpj
    * Artifact: UserService
    * Name: UserService
    * Description: Demo project for Spring Boot
    * Package name: com.zzpj.UserService
    * Packaging: Jar
    * Java: 21
    * Dependencies: Spring Web, Spring Security, oauth2-client
3. Create `application.yaml`

```yaml
server:
  port: 8081

spring:
  security:
    oauth2:
      client:
        registration:
          client:
            client-id: client
            client-secret: secret
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
            authorization-grant-type: authorization_code
            scope: openid, profile
        provider:
          client:
            issuer-uri: http://auth.mydomain.com:8080
```

4. Add `HomeController`
   ```java
   @Controller
   class HomeController {
   
       @GetMapping("/")
       public String home(@AuthenticationPrincipal OidcUser user) {
           return "Hi, " + user.getName();
       }
   }
   ```

## Keycloak
* zip/jar [Instalacja do pobrania](https://www.keycloak.org/downloads)
* lub [obraz dockerowy](https://quay.io/repository/keycloak/keycloak),
* [niezbędna dokumentacja](https://www.keycloak.org/guides#getting-started).
* [krok po kroku jak zacząć](https://www.keycloak.org/getting-started/getting-started-zip)

### Podstawowe pojęcia:

- Realm w Keycloak to izolowana przestrzeń, która zarządza własnymi użytkownikami, rolami, klientami i konfiguracją uwierzytelniania — działa jak niezależna domena bezpieczeństwa.


### Konfiguracja keycloak'a

1. Pobierz keycloak ze strony i rozpakuj
1. Otwórz conf/keycloak.conf i dodaj: `http-port=8999`
1. Uruchom keycloak w powershell: `bin\kc.bat start-dev`
1. Ustaw kredki dla root admin'a
1. Przedstaw poszczególne linki
1. Stwórz:
   - nowy realm
      - zakładka _General_
         - ustaw: Display name
      - zakładka _Login_
         - User registration: On
         - Forgot password: On
         - Remember me: On
         - Verify email: Off
      - zakładka _themes_
         - wszystko prefiksowane keycloak-
   - nowego klienta
      - zakładka _Credentials_
         - Client Authenticator: client id and secret
         - wygeneruj `Client secret`
      - zakładka _Settings_
         - root url: `http://localhost:8081` (port aplikacji będącej adapterem)
         - valid redirect urls: `/*`
         - Client authentication: on
         - Authorization: on
         - Login theme: keycloak
         - Authentication flow: Standard flow + **Direct acccess grants**
   - nowego użytkownika (pole 'Required user actions' puste) + ew jakieś role
1. Pobierz token
   - ogólny url: 'http://localhost:8999/realms/master/.well-known/openid-configuration'
    ```http request
    POST http://localhost:8999/realms/master/protocol/openid-connect/token
    Content-Type: application/x-www-form-urlencoded
    
    client_id = client &
    username = admin &
    password = admin123 &
    grant_type = password &
    client_secret = <client_secret>
    ```

### Aplikacja adaptera do keycloaka w Springu

1. start.spring.io: (lub użyj tej samej co Spring Authorization server)
   - wersja 3.5.0,
   - zależności: web, oauth2-client, security
   - Group: com.zzpj
   - Artifact: UserService

2. plik `application.properties`:
   ```yaml
   server:
      port: 8081
   
   spring:
      security:
         oauth2:
            client:
               registration:
                  client:
                     client-id: client
                     client-secret: <secret>
                     redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
                     authorization-grant-type: authorization_code
                     scope: openid, profile
               provider:
                  client:
                     issuer-uri: http://localhost:8999/realms/master
   ```
3. Sprawdzenie w przeglądarce: `http://localhost:8081/`

### Keycloak REST API

```xml
<dependency>
   <groupId>org.keycloak</groupId>
   <artifactId>keycloak-admin-client</artifactId>
   <version>26.0.5</version>
</dependency>
```

```java
@SpringBootApplication
public class TrainTripUsersAdapterApplication {

    private static final Logger log = LoggerFactory.getLogger(TrainTripUsersAdapterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TrainTripUsersAdapterApplication.class, args);
    }

    @Bean
    Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8999")
                .realm("master")
                .clientId("admin-cli")
                .grantType(OAuth2Constants.PASSWORD)
                .username("admin")
                .password("<password>")
                .build();
    }

    @Bean
    CommandLineRunner commandLineRunner(Keycloak keycloak) {
        return args -> {
            List<UserRepresentation> userRepresentations = keycloak.realm("master")
                    .users()
                    .search("<user>", false);
            List<String> list = userRepresentations.stream().map(AbstractUserRepresentation::getUsername).toList();
            return;
        };
    }
}
```