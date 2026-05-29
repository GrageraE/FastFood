package es.grupoO.FastFood.endpoint;

import es.grupoO.FastFood.containers.MongoContainer;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.hamcrest.Matchers.*;

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
    private static final String HORA_APERTURA = "10:00";
    private static final String HORA_CIERRE = "22:00";
    private static final String PASSWD = "1234";

    private static int contador = 1;

    private static String getEmail() {
        return "r" + (contador++) + "@r.com";
    }

    @BeforeEach
    void setUp() {
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
        String email = getEmail();
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        email,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD

                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(200)
                .and()
                .body("nombre", equalTo(NOMBRE))
                .and()
                .body("telefono", equalTo(TELEFONO))
                .and()
                .body("categoria", equalTo(CategoriaRestaurante.fromInteger(CATEGORIA).toString()))
                .and()
                .body("email.userName", equalTo(Email.parse(email).get().getUserName()))
                .and()
                .body("email.servidor", equalTo(Email.parse(email).get().getServidor()))
                .and()
                .body("horaApertura", equalTo(HORA_APERTURA + ":00"))
                .and()
                .body("horaCierre", equalTo(HORA_CIERRE + ":00"));
    }

    @Test
    void TC2() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        -1,
                        DIRECCION,
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
                .statusCode(400)
                .and()
                .body("message", equalTo("Numero de categoria de restaurante invalido"));
    }

    @Test
    void TC3() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        4,
                        DIRECCION,
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
                        "AA",
                        DIRECCION,
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

    @Test
    void TC5() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        "efmkerkjgioerjgergioerg",
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
                .statusCode(400)
                .body("message", equalTo("Sin resultados"));
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
                        DIRECCION,
                        TELEFONO,
                        4,
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", equalTo("El email proporcionado no es correcto"));
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
                        DIRECCION,
                        TELEFONO,
                        "prueba",
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", equalTo("El email proporcionado no es correcto"));
    }

    @Test
    void TC8() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        "@gmail.com",
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", equalTo("El email proporcionado no es correcto"));
    }

    @Test
    void TC9() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        "restaurante@",
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", equalTo("El email proporcionado no es correcto"));
    }

    @Test
    void TC10() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        "@",
                        HORA_APERTURA,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", equalTo("El email proporcionado no es correcto"));
    }

    @Test
    void TC11() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        1500,
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + 1500));
    }

    @Test
    void TC12() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "hora",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "hora"));
    }

    @Test
    void TC13() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "prueba:20",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "prueba:20"));
    }

    @Test
    void TC14() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "12:prueba",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "12:prueba"));
    }

    @Test
    void TC15() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "-2:15",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "-2:15"));
    }


    @Test
    void TC16() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "24:01",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "24:01"));
    }


    @Test
    void TC17() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "12:-2",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "12:-2"));

    }


    @Test
    void TC18() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        "12:60",
                        HORA_CIERRE,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "12:60"));

    }

    @Test
    void TC19() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        14,
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + 14));
    }

    @Test
    void TC20() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "prueba",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "prueba"));
    }

    @Test
    void TC21() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "prueba:15",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "prueba:15"));
    }

    @Test
    void TC22() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "12:prueba",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "12:prueba"));
    }


    @Test
    void TC23() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "-2:15",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "-2:15"));
    }


    @Test
    void TC24() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "26:01",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "26:01"));
    }


    @Test
    void TC25() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "12:-2",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "12:-2"));

    }


    @Test
    void TC26() {
        RestAssured
                .given()
                .baseUri(BASE_URI)
                .port(this.port)
                .body(this.getBody(
                        NOMBRE,
                        CATEGORIA,
                        DIRECCION,
                        TELEFONO,
                        getEmail(),
                        HORA_APERTURA,
                        "12:65",
                        PASSWD
                ))
                .contentType("application/json")
                .when()
                .post("/restaurante/registro")
                .then()
                .statusCode(400)
                .body("message", startsWith("Tiempo invalido: " + "12:65"));

    }

}
