package es.grupoO.FastFood.endpoint;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import es.grupoO.FastFood.containers.MongoContainer;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class GetPlatosTest implements MongoContainer {
    @LocalServerPort
    private int port;
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
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        15,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400);
    }
}