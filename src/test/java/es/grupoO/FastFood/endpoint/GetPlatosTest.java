package es.grupoO.FastFood.endpoint;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testcontainers.junit.jupiter.Testcontainers;

import es.grupoO.FastFood.containers.MongoContainer;
import es.grupoO.FastFood.model.entity.Restaurante;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class GetPlatosTest implements MongoContainer {
    @LocalServerPort
    private int port;
    @Autowired
    private Restaurante restaurantesService;
    public static final String BASE_URI = "http://localhost";

    private static final String NOMBRE = "R1";
    private static final int CATEGORIA = 1;
    private static final String DIRECCION = "Gran Vía, Madrid, España";
    private static final String TELEFONO = "51464";
    private static final String HORA_APERTURA = "10:00";
    private static final String HORA_CIERRE = "22:00";
    private static final String PASSWD = "1234";

    private static int contador = 1;

    private static String getEmail() {
        return "r" + (contador++) + "@r.com";
    }

    @BeforeEach
    void setUp() {
        // Si la prueba falla, imprime el detalle en consola automáticamente
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void createRestaurante() {
        String email = getEmail();
        Restaurante restaurante = this.restaurantesService.insertarRestaurante(
                NOMBRE,
                CATEGORIA,
                DIRECCION,
                TELEFONO,
                email,
                HORA_APERTURA,
                HORA_CIERRE,
                PASSWD
        );
    }

    Map<String, Object> getBody(Object nombre, Object categoria, Object direccion, Object telefono, Object email,
                                Object horaApertura, Object horaCierre, Object passwd)
    {
        return Map.of(
                "nombre", nombre,
                "categoria", categoria,
                "direccion", direccion,
                "telefono", telefono,
                "email", email,
                "horaApertura", horaApertura,
                "horaCierre", horaCierre,
                "passwd", passwd
        );
    }

    @Test
    void TC1() { 
    String idRestaurante = "1";
    RestAssured
        .given()
            .contentType("application/json")
            .pathParam("idRestaurante", idRestaurante)
        .when()
            .get("/restaurante/{idRestaurante}/platos")
        .then()
            .statusCode(200);
    }

    @Test
    void TC3() { 
    String idRestaurante = "restaurante";
    RestAssured
        .given()
            .contentType("application/json")
            .pathParam("idRestaurante", idRestaurante)
        .when()
            .get("/restaurante/{idRestaurante}/platos")
        .then()
            .statusCode(400);
    }

    @Test
    void TC4() { 
    Integer idRestaurante = -1;
    RestAssured
        .given()
            .contentType("application/json")
            .pathParam("idRestaurante", idRestaurante)
        .when()
            .get("/restaurante/{idRestaurante}/platos")
        .then()
            .statusCode(400);
    }

    @Test
    void TC5() { 
    Integer idRestaurante = -1;
    RestAssured
        .given()
            .contentType("application/json")
            .pathParam("idRestaurante", idRestaurante)
        .when()
            .get("/restaurante/{idRestaurante}/platos")
        .then()
            .statusCode(400);
    }
}