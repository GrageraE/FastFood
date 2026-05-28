package es.grupoO.FastFood.endpoint;

import es.grupoO.FastFood.factory.RestauranteFactory;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.services.GeocodingService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostPlatosTest {
    @Container
    @ServiceConnection
    static MongoDBContainer mongoContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @LocalServerPort
    private int port;

    @Autowired
    MongoTemplate mongoTemplate;

    public static final String EMAIL = "r@r.com";
    public static final String PASSWD = "1234";

    public static final String BASE_URI = "http://localhost";

    private static final String NOMBRE = "R1";
    private static final int CATEGORIA = 1;
    private static final double PRECIO = 15.0;

    private static String idRestaurante = null;
    // Ya lleva el 'Bearer: '
    private static String tokenRestaurante = null;

    @BeforeEach
    void setUpTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private Restaurante construirRestaurante(String email, String passwd) {
        RestauranteFactory fact = new RestauranteFactory(
                "R",
                "Plaza Mayor, Madrid, España",
                "12345",
                "05:00",
                "22:00",
                2,
                email,
                passwd,
                new GeocodingService()
        );

        Restaurante rest = fact.fabricarRestaurante();

        mongoTemplate.insert(rest.getValoracion());
        mongoTemplate.insert(rest);
        return rest;
    }

    private String inicizalizarTokenAuth() {
        String token = RestAssured.given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(Map.of(
                        "email", EMAIL,
                        "passwd", PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/validar")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .and()
                .extract().path("token");
        return token;
    }

    @BeforeAll
    void setUpRestaurante() {
        Restaurante rest = construirRestaurante(EMAIL, PASSWD);
        idRestaurante = rest.getIdRestaurante();
        tokenRestaurante = inicizalizarTokenAuth();
    }

//    @AfterAll
//    void cleanUpRestaurante() {
//        mongoTemplate.getDb().drop();
//    }

    @Test
    void TC1() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(200);
    }

    // TODO: Agregar mas tests
}
