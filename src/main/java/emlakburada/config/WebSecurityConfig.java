package emlakburada.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import emlakburada.filter.JwtFilter;



@Configuration
public class WebSecurityConfig{

//	@Bean
//	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//		//// @formatter:off
//		http
//		.httpBasic().disable()
//		.formLogin().disable()
//		
//		.authorizeExchange()
//		.pathMatchers(HttpMethod.GET, "/adverts/**")
//		.permitAll()
//		.pathMatchers(HttpMethod.POST, "/users/**")
//		.permitAll()
//		.pathMatchers(HttpMethod.POST, "/auth")
//		.permitAll()
//		.and()
//		.csrf().disable()
//		.authorizeExchange()
//		.anyExchange()
//		.authenticated();
//		
//		return http.build();
//		// @formatter:on
//
//	}
//
	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		//// @formatter:off
		http
		.httpBasic().disable()
		.formLogin().disable()
		.csrf().disable();
		
		return http.build();
		// @formatter:on

	}

	@Autowired
	private JwtFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
		//// @formatter:off
 

				.route("auth", 
						r -> r.path("/auth/**")
							  .uri("http://localhost:8082"))
				.route("emlakburada", 
						r -> r.method(HttpMethod.POST)
						.and()
						.path(("/adverts/**"))
						.filters(f -> f.filter(filter)).uri("lb://emlakburada"))
				.route("emlakburada", 
						r -> r.method(HttpMethod.GET)
						.and()
						.path("/users/**")
						.filters(f -> f.filter(filter)).uri("lb://emlakburada"))
				.route("emlakburada-banner",
						r -> r.path("/banners/**")
						.filters(f -> f.filter(filter)).uri("http://localhost:8081"))
				.build();
		
		// @formatter:on
	}

}
