package net.smartcosmos.cluster.userdetails;

import lombok.extern.slf4j.Slf4j;
import net.smartcosmos.spring.EnableSmartCosmos;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author voor
 */
@SpringBootApplication
@EnableSmartCosmos
@Slf4j
public class UserDetailsService {

	public static void main(String[] args) {
		new SpringApplicationBuilder(UserDetailsService.class).web(true).run(args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
