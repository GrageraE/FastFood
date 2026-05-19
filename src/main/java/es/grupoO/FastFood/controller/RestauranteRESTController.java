package es.grupoO.FastFood.controller;

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
    public RestauranteLoginDTO validarRestaurante(@RequestBody String email, @RequestBody String password) {
        return restaurantesService.validar(email, password);
    }

    @PostMapping("/restaurante/registro")
    public Restaurante insertarRestaurante(@RequestBody String nombre, @RequestBody int categoria, @RequestBody String direccion,
                                    @RequestBody String telefono, @RequestBody  String email, @RequestBody String horaApertura,
                                    @RequestBody String horaCierre, @RequestBody String passwd)
    {
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
    public void insertarPlato(@PathVariable String idRest, @RequestBody String nombre, @RequestBody int categoria, @RequestBody double precio) {
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
    public void establecerRebaja(@PathVariable String idRest, @PathVariable String idPlato, @RequestBody double nuevoPrecio, @RequestBody String fecha) {
        this.platosService.establecerRebaja(idRest, idPlato, nuevoPrecio, fecha);
    }

    @PutMapping("/restaurante/password")
    @SecurityRequirement(name = "authorization")
    public void changePasswdRestaurante(@RequestBody String newPasswd,
                            Authentication auth) {
        this.restaurantesService.changePasswdRestaurante(newPasswd, auth);
    }
}
