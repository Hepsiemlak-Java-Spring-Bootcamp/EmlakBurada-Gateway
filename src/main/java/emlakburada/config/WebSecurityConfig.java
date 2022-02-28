package emlakburada.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class WebSecurityConfig {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		//// @formatter:off
		http
		.httpBasic()
		.and()
		.authorizeExchange()
		.pathMatchers(HttpMethod.GET, "/adverts")
		.permitAll()
		.pathMatchers(HttpMethod.POST, "/users")
		.permitAll()
		.and()
		.csrf().disable()
		.authorizeExchange()
		.anyExchange()
		.authenticated();
		
		return http.build();
		// @formatter:on

	}

}
