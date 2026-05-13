package es.grupoO.FastFood;

import es.grupoO.FastFood.auth.FilterInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "es.grupoO.FastFood.repository")
public class FastFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastFoodApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig {
		@Bean
		protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
			http
					.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(auth -> auth
							.requestMatchers(HttpMethod.POST, "/clientes/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/clientes/validar").permitAll()
							.requestMatchers(HttpMethod.POST, "/restaurantes/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/restaurantes/validar").permitAll()
							.requestMatchers(HttpMethod.POST, "/repartidores/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/repartidores/validar").permitAll()

							.requestMatchers(HttpMethod.GET, "/clientes/**").hasRole("CLIENTE")
							.requestMatchers(HttpMethod.POST, "/restaurantes/**/valoracion").hasRole("CLIENTE")
							.requestMatchers(HttpMethod.POST, "/pedidos/**/asignar").hasRole("REPARTIDOR")
							.requestMatchers(HttpMethod.POST, "/pedidos/**/estado").hasRole("RESTAURANTE")
							.requestMatchers(HttpMethod.POST, "/restaurantes/**/platos/**/rebaja").hasRole("RESTAURANTE")
							.anyRequest().authenticated()
					)
					.addFilterAfter(new FilterInterceptor(), UsernamePasswordAuthenticationFilter.class);
			return http.build();
		}
	}
}
