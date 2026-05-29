package es.grupoO.FastFood.endpoint;

import es.grupoO.FastFood.factory.RestauranteFactory;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.factory.ClienteFactory;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.factory.RepartidorFactory;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.entity.Plato;

import es.grupoO.FastFood.services.GeocodingService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostPlatosTest {
    @Container
    @ServiceConnection
    static MongoDBContainer mongoContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @LocalServerPort
    private int port;

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String EMAIL_PRINCIPAL = "r@r.com";
    private static final String EMAIL_SECUNDARIO = "rr@rr.com";
    private static final String EMAIL_CLIENTE = "cl@cl.com";
    private static final String EMAIL_REPARTIDOR = "rep@rep.com";
    private static final String PASSWD = "1234";

    private static final String BASE_URI = "http://localhost";

    private static final String NOMBRE = "R1";
    private static final int CATEGORIA = 1;
    private static final double PRECIO = 15.0;

    private static String idRestaurante = null;
    // Ya lleva el 'Bearer: '
    private static String tokenRestaurante = null;

    private static int contador = 1;

    @BeforeEach
    void setUpTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private Restaurante construirRestaurante(String email, String passwd) {
        RestauranteFactory fact = new RestauranteFactory(
                "R" + (contador++),
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

    private Cliente construirCliente(String email, String passwd) {
        ClienteFactory fact = new ClienteFactory(
                "Cl",
                "Plaza Mayor, Madrid, España",
                "12345",
                email,
                passwd
        );

        Cliente rest = fact.crearCliente();

        mongoTemplate.insert(rest);
        return rest;
    }

    private Repartidor construirRepartidor(String email, String passwd) {
        RepartidorFactory fact = new RepartidorFactory(
                "Rep",
                "12345",
                email,
                passwd
        );

        Repartidor rest = fact.fabricarRepartidor();

        mongoTemplate.insert(rest);
        return rest;
    }

    private String iniciarSesion(String email, String passwd, String endpoint) {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(Map.of(
                        "email", email,
                        "passwd", passwd
                ))
                .contentType("application/json")
                .when()
                .post(String.format("/%s/validar", endpoint))
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .and()
                .extract().path("token");
    }

    private void comprobarPlatoInsertado(String nombrePlato) {
        Query query = new Query();
        query.addCriteria(Criteria.where("restaurante.$id").is(new ObjectId(idRestaurante)));

        Plato plato = mongoTemplate.findOne(query, Plato.class);
        assertNotNull(plato);
        assertEquals(nombrePlato, plato.getNombre());
    }

    @BeforeAll
    void setUpRestaurante() {
        var restPrincipal = construirRestaurante(EMAIL_PRINCIPAL, PASSWD);
        construirRestaurante(EMAIL_SECUNDARIO, PASSWD);
        construirCliente(EMAIL_CLIENTE, PASSWD);
        construirRepartidor(EMAIL_REPARTIDOR, PASSWD);

        idRestaurante = restPrincipal.getIdRestaurante();
        tokenRestaurante = iniciarSesion(EMAIL_PRINCIPAL, PASSWD, "restaurante");
    }

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
        comprobarPlatoInsertado(NOMBRE);
    }

    @Test
    void TC2() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", 35)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(404)
                .and()
                .body("message", equalTo(("El restaurante no existe")));
    }


    @Test
    void TC3() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", "ID no válido")
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(404)
                .and()
                .body("message", equalTo(("El restaurante no existe")));
    }


    @Test
    void TC4() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", -2)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo(("Numero de categoria de plato invalido")));
    }

    @Test
    void TC5() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", 5)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo(("Numero de categoria de plato invalido")));
    }

    @Test
    void TC6() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", "PIZZA")
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(400)
                .and()
                .body("error", equalTo("Bad Request"));
    }

    @Test
    void TC7() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestaurante)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", "dinero")
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(400)
                .and()
                .body("error", equalTo("Bad Request"));
    }

    @Test
    void TC8() {
        String tokenCliente = iniciarSesion(EMAIL_CLIENTE, PASSWD, "cliente");
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenCliente)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(403)
                .and()
                .body("error", equalTo("Forbidden"));
    }

    @Test
    void TC9() {
        String tokenRepartidor = iniciarSesion(EMAIL_REPARTIDOR, PASSWD, "repartidor");
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRepartidor)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(403)
                .and()
                .body("error", equalTo("Forbidden"));
    }

    @Test
    void TC10() {
        String tokenRestSecundario = iniciarSesion(EMAIL_SECUNDARIO, PASSWD, "restaurante");
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", tokenRestSecundario)
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(403)
                .and()
                .body("message", equalTo(("El restaurante autenticado no coincide con el ID dado")));
    }

    @Test
    void TC11() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .contentType("application/json")
                .header("Authorization", "Token incorrecto ¡¡!! ññ")
                .pathParam("idRest", idRestaurante)
                .queryParam("nombre", NOMBRE)
                .queryParam("categoria", CATEGORIA)
                .queryParam("precio", PRECIO)
                .when()
                .post("/restaurante/{idRest}/platos")
                .then()
                .statusCode(403)
                .and()
                .body("error", equalTo("Forbidden"));
    }
}
