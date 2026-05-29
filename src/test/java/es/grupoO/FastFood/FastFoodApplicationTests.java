package es.grupoO.FastFood;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import es.grupoO.FastFood.services.ClientesService;

@SpringBootTest
@Testcontainers
class FastFoodApplicationTests {
	@Container
	@ServiceConnection
	static MongoDBContainer mongoTestContainer
			= new MongoDBContainer(DockerImageName.parse("mongo:latest"));

	@Autowired
	private ClientesService clientesService;

	@Test
	void contextLoads() {
	}
}
