package es.grupoO.FastFood;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import es.grupoO.FastFood.exceptions.NoMatchingPasswordException;
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

	@Test
	void testPrueba() {
		this.clientesService.insertarCliente(
				"Cl1",
				"A",
				"6625",
				"cl@cl.com",
				"1234"
		);

		this.clientesService.validar("cl@cl.com", "1234");

		assertThrows(NoMatchingPasswordException.class,
				() -> this.clientesService.validar("cl@cl.com", "5678"));
	}

}
