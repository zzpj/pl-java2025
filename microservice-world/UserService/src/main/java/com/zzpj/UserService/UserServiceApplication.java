package com.zzpj.UserService;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.AbstractUserRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	Keycloak keycloak() {
		return KeycloakBuilder.builder()
				.serverUrl("http://localhost:8999")
				.realm("master")
				.clientId("admin-cli")
				.grantType(OAuth2Constants.PASSWORD)
				.username("admin")
				.password("admin123")
				.build();
	}

	@Bean
	CommandLineRunner commandLineRunner(Keycloak keycloak) {
		return args -> {
			List<UserRepresentation> userRepresentations = keycloak.realm("master")
					.users()
					.search("admin", false);
			List<String> list = userRepresentations.stream().map(AbstractUserRepresentation::getUsername).toList();
			return;
		};
	}
}

@Controller
class HomeController {

	@GetMapping("/hello")
	public ResponseEntity<String> home(@AuthenticationPrincipal OidcUser user) {
		String hello = "Hi, " + user.getName();
		System.out.println(hello);
		return ResponseEntity.ok(hello);
	}
}