package com.zzpj.TrainTripsUserService;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;

@SpringBootApplication
@EnableWebSecurity
public class TrainTripsUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainTripsUserServiceApplication.class, args);
    }

}

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
                .authorizeHttpRequests(request -> request.requestMatchers("/external", "/token").permitAll()
                        .requestMatchers("/validate").authenticated())
                .userDetailsService(userDetailsService())
                .formLogin(e -> e.defaultSuccessUrl("/internal", true))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(new JwtAuthenticationConverter())))

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

@RestController
@RequiredArgsConstructor
class UserController {

    @GetMapping("/internal")
    public String getInternalMessage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return String.format("Hello %s!", user.getUsername());
    }

    @GetMapping("/external")
    public String getExternalMessage() {
        return "Hello all viewers!";
    }

    private final JwtEncoder jwtEncoder;

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
        System.out.println("tokenValue: " +  tokenValue);
        return ResponseEntity.ok(tokenValue);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok("Token correct for user: " + jwt.getSubject());
    }

}

@Data
class AuthRequest {
    private String username;
    private String password;
}