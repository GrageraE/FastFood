package es.grupoO.FastFood.endpoint;

import es.grupoO.FastFood.common.MongoContainer;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class RestauranteRegistroTest implements MongoContainer {
    @LocalServerPort
    private int port;

    public static final String BASE_URI = "http://localhost";

    private static final String NOMBRE = "R1";
    private static final int CATEGORIA = 1;
    private static final String DIRECCION = "Gran Vía, Madrid, España";
    private static final String TELEFONO = "51464";
    private static final String EMAIL = "r1@r1.com";
    private static final String HORA_APERTURA = "10:00";
    private static final String HORA_CIERRE = "22:00";
    private static final String PASSWD = "1234";


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
                        DIRECCION,
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD

                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(200)
                .and()
                .body("NOMBRE", equalTo(NOMBRE))
                .and()
                .body("TELEFONO", equalTo(TELEFONO))
                .and()
                .body("CATEGORIA", equalTo(CategoriaRestaurante.fromInteger(CATEGORIA).toString()))
                .and()
                .body("EMAIL", equalTo(EMAIL))
                .and()
                .body("HORA_APERTURA", equalTo(HORA_APERTURA))
                .and()
                .body("HORA_CIERRE", equalTo(HORA_CIERRE));
    }

    @Test
    void TC2() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        20,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400);
    }

    @Test
    void TC3() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        -1,
                        DIRECCION,
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Numero de categoria de restaurante invalido"));
    }

    @Test
    void TC4() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        4,
                        DIRECCION,
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .and()
                .body("message", equalTo("Numero de categoria de restaurante invalido"));
    }

    @Test
    void TC5() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        "AA",
                        DIRECCION,
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400);
    }

    @Test
    void TC6() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        15,
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400);
    }

    @Test
    void TC7() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        "efmkerkjgioerjgergioerg",
                        TELEFONO,
                        EMAIL,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", equalTo("Sin resultados"));
    }

}
