package es.grupoO.FastFood;

// Aplicacion basica
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// Repositorios Mongo
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
// Autenticacion y Spring Security
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import es.grupoO.FastFood.auth.FilterInterceptor;
// Configuracion de Swagger para el manejo de los requisitos de autenticacion
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
// Configuracion de Swagger para el manejo de ObjectID
import io.swagger.v3.oas.models.media.StringSchema;
import org.bson.types.ObjectId;
import org.springdoc.core.utils.SpringDocUtils;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "es.grupoO.FastFood.repository")
public class FastFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastFoodApplication.class, args);
	}

	@Configuration
	class SwaggerConfig {
		static {
			SpringDocUtils.getConfig().replaceWithSchema(ObjectId.class, new StringSchema());
		}

		@Bean
		public OpenAPI customOpenAPI() {
			// Habilita la opcion de proporcionar un token JWT en Swagger
			return new OpenAPI()
					.addSecurityItem(new SecurityRequirement().addList("authorization"))
					.components(new io.swagger.v3.oas.models.Components()
							.addSecuritySchemes("authorization",
									new SecurityScheme()
											.name("authorization")
											.type(SecurityScheme.Type.HTTP)
											.scheme("bearer")
											.bearerFormat("JWT")
											.in(SecurityScheme.In.HEADER)
											.description("JWT auth description")));
		}

		@Bean
		public GroupedOpenApi publicApi() {
			return GroupedOpenApi.builder()
					.group("public")
					.pathsToMatch("/**")
					.build();
		}
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig {
		@Bean
		protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
			// Establece los requisitos de autenticacion de los endpoints
			http
					.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(auth -> auth
							// Permitir registro y login sin necesidad de autenticarse previamente
							.requestMatchers(HttpMethod.POST, "/clientes/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/clientes/validar").permitAll()
							.requestMatchers(HttpMethod.POST, "/restaurantes/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/restaurantes/validar").permitAll()
							.requestMatchers(HttpMethod.POST, "/repartidores/registro").permitAll()
							.requestMatchers(HttpMethod.POST, "/repartidores/validar").permitAll()
							// Exigir roles especificos para determinados endpoints
							.requestMatchers(HttpMethod.GET, "/clientes/{id}").hasAuthority("CLIENTE")
							.requestMatchers(HttpMethod.POST, "/restaurantes/{id}/valoracion").hasAuthority("CLIENTE")
							.requestMatchers(HttpMethod.POST, "/pedidos/{id}/asignar").hasAuthority("REPARTIDOR")
							.requestMatchers(HttpMethod.POST, "/pedidos/{id}/estado").hasAuthority("RESTAURANTE")
							.requestMatchers(HttpMethod.POST, "/restaurantes/{id_rest}/platos/{id_plato}/rebaja").hasAuthority("RESTAURANTE")
							// Permitir usar swagger sin autenticarse
							.requestMatchers("/swagger-ui.html").permitAll()
							.requestMatchers("/swagger-ui/**").permitAll()
							.requestMatchers("/v3/api-docs.yaml").permitAll()
							.requestMatchers("/v3/api-docs/**").permitAll()
							// Resto de endpoints: requieren simplemente estar autenticados
							.anyRequest().authenticated()
					)
					.addFilterBefore(new FilterInterceptor(), UsernamePasswordAuthenticationFilter.class);
			return http.build();
		}
	}
}
