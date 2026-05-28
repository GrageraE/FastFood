package es.grupoO.FastFood.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import es.grupoO.FastFood.dto.PlatoDTO;
import es.grupoO.FastFood.exceptions.InvalidDateException;
import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.exceptions.NotValidEmailException;
import es.grupoO.FastFood.exceptions.RoleNotAllowedException;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.services.RestaurantesService;

@SpringBootTest
@Testcontainers
class EstablecerRebajaTests {
    @Container
	@ServiceConnection
	static MongoDBContainer mongoTestContainer
			= new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Autowired
    PlatosService platosService;

    @Autowired
    RestaurantesService restaurantesService;

    Authentication auth;

    Restaurante restaurante;
    Plato plato;

    //Inicializamos un restaurante con su login y un plato para cada test
    @BeforeEach
    public void insertarRestauranteConLoginYInsertarPlato(){
        restaurante = restaurantesService.insertarRestaurante("test", 0, "C. de Canarias, 47-43, Arganzuela, 28045 Madrid", "123456789", "test@test.com", "10:00", "22:00", "test");
        auth = new UsernamePasswordAuthenticationToken(
                "test@test.com", 
                null,
                List.of(new SimpleGrantedAuthority("RESTAURANTE"))
        );

        platosService.insertarPlato(restaurante.getIdRestaurante(), "plato", 0, 1, auth);
        List<PlatoDTO> platos = platosService.buscarPlato(restaurante.getIdRestaurante());
        plato = platosService.buscarPlatoPorID(platos.get(0).getIdPlato());
    }

    //Se crea una rebaja tras cada llamada a test, luego se elimina para limpieza
    @AfterEach 
    public void eliminarPlatoYRestauranteYRebaja(){
        if(platosService.buscarPlatoPorID(plato.getIdPlato()).getRebaja() != null){
            platosService.quitarRebaja(plato.getIdPlato(), auth);
        }
        platosService.borrarPlato(plato.getIdPlato(), auth);
        restaurantesService.borrarRestaurante(auth);
    }
    // Nodo B — Fecha con buen formato
    @Test
    public void testConBuenFormatoDate(){
        String buenFormatoDate= "2027-08-28";
        assertDoesNotThrow(() -> {
            platosService.establecerRebaja(plato.getIdPlato(), 6, buenFormatoDate, auth);
        });
    }

    // Nodo C — Fecha con mal formato
    @Test
    public void testConMalFormatoDate(){
        String malFormatoDate = "2027/08/28";
        assertThrows(InvalidDateException.class, () -> {
            platosService.establecerRebaja(plato.getIdPlato(), 6, malFormatoDate, auth);
        });
    }
    // Nodo E — Email no válido 
    @Test
    public void testConEmailInvalido() {
        Authentication authMala = new UsernamePasswordAuthenticationToken(
            "emailsinformato", null,
            List.of(new SimpleGrantedAuthority("RESTAURANTE"))
        );
        assertThrows(NotValidEmailException.class, () -> {
            platosService.establecerRebaja(plato.getIdPlato(), 6.0, "2027-08-28", authMala);
        });
    }
    // Nodo D — Email válido
    @Test
    public void testConEmailValido() {
        assertDoesNotThrow(() -> {
            platosService.establecerRebaja(plato.getIdPlato(), 6.0, "2027-08-28", auth);
        });
    }

        // Nodo G — Plato que no existe en BD
    @Test
    public void testConPlatoInexistente(){
        assertThrows(NoExistDBException.class, () -> {
            platosService.establecerRebaja("idFalso123", 6, "2027-08-28", auth);
        });
    }
    // Nodo F — Plato que existe en BD
    @Test
    public void testConPlatoExistente(){
        assertDoesNotThrow(() -> platosService.establecerRebaja(plato.getIdPlato(), 10, "2027-08-28", auth));
    }

    // Nodo I — Plato de otro restaurante 
    @Test
    public void testConPlatoDeOtroRestaurante(){
        // Crear segundo restaurante
        restaurantesService.insertarRestaurante("otro", 0, "C. de Canarias, 47-43, Arganzuela, 28045 Madrid",
            "987654321", "otro@otro.com", "10:00", "22:00", "otro");
        Authentication authOtro = new UsernamePasswordAuthenticationToken(
            "otro@otro.com", null,
            List.of(new SimpleGrantedAuthority("RESTAURANTE"))
        );
        // Intentar poner rebaja en plato del primer restaurante con token del segundo
        assertThrows(RoleNotAllowedException.class, () -> {
            platosService.establecerRebaja(plato.getIdPlato(), 6, "2027-08-28", authOtro);
        });

        restaurantesService.borrarRestaurante(authOtro);
    }

    // Nodo H — Plato del restaurante correcto
    @Test
    public void testConPlatoDelRestauranteCorrecto(){
        // Crear segundo restaurante
        restaurantesService.insertarRestaurante("otro", 0, "C. de Canarias, 47-43, Arganzuela, 28045 Madrid",
            "987654321", "otro@otro.com", "10:00", "22:00", "otro");
        Authentication authOtro = new UsernamePasswordAuthenticationToken(
            "otro@otro.com", null,
            List.of(new SimpleGrantedAuthority("RESTAURANTE"))
        );
        // Intentar poner rebaja en plato del primer restaurante con token del segundo
        assertThrows(RoleNotAllowedException.class, () -> {
            platosService.establecerRebaja(plato.getIdPlato(), 6, "2027-08-28", authOtro);
        });

        restaurantesService.borrarRestaurante(authOtro);
    }

    // Nodo J — Rebaja guardada correctamente en persistencia
    @Test
    public void buenGuardadoPersistencia(){
        platosService.establecerRebaja(plato.getIdPlato(), 15, "2027-08-28", auth);
        Plato platoConRebaja = platosService.buscarPlatoPorID(plato.getIdPlato());
        assertEquals((double)15, platoConRebaja.getPrecio().getCantidad());
    }
}
