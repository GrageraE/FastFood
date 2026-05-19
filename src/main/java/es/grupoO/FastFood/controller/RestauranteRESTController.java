package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.dto.FormLoginDTO;
import es.grupoO.FastFood.dto.RestauranteInsertDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import es.grupoO.FastFood.services.RestaurantesService;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.services.PedidosService;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Plato;
import org.springframework.security.core.Authentication;
import es.grupoO.FastFood.dto.RestauranteLoginDTO;

import java.util.List;
import java.util.Map;

@RestController
public class RestauranteRESTController {
    @Autowired
    private final RestaurantesService restaurantesService;
    @Autowired
    private final PlatosService platosService;
    @Autowired
    private final PedidosService pedidosService;

    public RestauranteRESTController(RestaurantesService restaurantesService, PlatosService platosService, PedidosService pedidosService) {
        this.restaurantesService = restaurantesService;
        this.platosService = platosService;
        this.pedidosService = pedidosService;
    }

    @PostMapping("/restaurante/validar")
    public RestauranteLoginDTO validarRestaurante(@RequestBody FormLoginDTO form) {
        String email = form.getEmail();
        String password = form.getPasswd();
        return restaurantesService.validar(email, password);
    }

    @PostMapping("/restaurante/registro")
    public Restaurante insertarRestaurante(@RequestBody RestauranteInsertDTO insertDTO)
    {
        String nombre = insertDTO.getNombre();
        int categoria = insertDTO.getCategoria();
        String direccion = insertDTO.getDireccion();
        String telefono = insertDTO.getTelefono();
        String email = insertDTO.getEmail();
        String horaApertura = insertDTO.getHoraApertura();
        String horaCierre = insertDTO.getHoraCierre();
        String passwd = insertDTO.getPasswd();
        return this.restaurantesService.insertarRestaurante(nombre, categoria, direccion, telefono, email, horaApertura, horaCierre, passwd);
    }

    @GetMapping("/restaurante/{idRest}")
    @SecurityRequirement(name = "authorization")
    public Restaurante buscarRestaurantePorID(@PathVariable String idRest) {
        return this.restaurantesService.buscarRestaurantePorID(idRest);
    }

    @DeleteMapping("/restaurante/{idRest}")
    @SecurityRequirement(name = "authorization")
    public void borrarRestaurante(@PathVariable String idRest) {
        this.restaurantesService.borrarRestaurante(idRest);
    }

    @GetMapping("/restaurante/{idRest}/platos/{categoria}")
    @SecurityRequirement(name = "authorization")
    public List<Plato> filtrarPlatos(@PathVariable String idRest, @PathVariable int categoria) {
        return this.platosService.filtrarPlatos(idRest, categoria);
    }

    @PostMapping("/restaurante/{idRest}/platos")
    @SecurityRequirement(name = "authorization")
    public void insertarPlato(@PathVariable String idRest, @RequestParam String nombre, @RequestParam int categoria, @RequestParam double precio) {
        this.platosService.insertarPlato(idRest, nombre, categoria, precio);
    }

    @DeleteMapping("/restaurante/{_idRest}/platos/{idPlato}")
    @SecurityRequirement(name = "authorization")
    public void borrarPlato(@PathVariable String _idRest, @PathVariable String idPlato) {
        this.platosService.borrarPlato(idPlato);
    }

    @PostMapping("/pedidos/{idPedido}/estado")
    @SecurityRequirement(name = "authorization")
    public void cambiarEstadoPedido(@PathVariable String idPedido, @RequestBody int estado) {
        this.pedidosService.cambiarEstado(idPedido, estado);
    }

    @PostMapping("/restaurante/{idRest}/platos/{idPlato}/rebaja")
    @SecurityRequirement(name = "authorization")
    public void establecerRebaja(@PathVariable String idRest, @PathVariable String idPlato, @RequestParam double nuevoPrecio, @RequestParam String fecha) {
        this.platosService.establecerRebaja(idRest, idPlato, nuevoPrecio, fecha);
    }

    @PutMapping("/restaurante/password")
    @SecurityRequirement(name = "authorization")
    public void changePasswdRestaurante(@RequestBody String newPasswd,
                            Authentication auth) {
        this.restaurantesService.changePasswdRestaurante(newPasswd, auth);
    }
}
