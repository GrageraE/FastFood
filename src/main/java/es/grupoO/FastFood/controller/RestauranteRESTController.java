package es.grupoO.FastFood.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import es.grupoO.FastFood.services.RestaurantesService;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.services.PedidosService;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Plato;

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

    @PostMapping("/restaurantes/validar")
    public RestauranteLoginDTO validarRestaurante(@RequestParam String email, @RequestParam String password) {
        return restaurantesService.validar(email, password);
    }

    @PostMapping("/restaurantes/registro")
    public Restaurante insertarRestaurante(@RequestParam String nombre, @RequestParam int categoria, @RequestParam  String direccion,
                                    @RequestParam String telefono, @RequestParam  String email, @RequestParam String horaApertura,
                                    @RequestParam String horaCierre, @RequestParam String passwd)
    {
        return this.restaurantesService.insertarRestaurante(nombre, categoria, direccion, telefono, email, horaApertura, horaCierre, passwd);
    }

    @GetMapping("/restaurantes/{idRest}")
    @SecurityRequirement(name = "authorization")
    public Restaurante buscarRestaurantePorID(@PathVariable String idRest) {
        return this.restaurantesService.buscarRestaurantePorID(idRest);
    }

    @DeleteMapping("/restaurante/{idRest}")
    @SecurityRequirement(name = "authorization")
    public void borrarRestaurante(@PathVariable String idRest) {
        this.restaurantesService.borrarRestaurante(idRest);
    }

//    @GetMapping("/restaurante/{idRest}/platos")
//    @SecurityRequirement(name = "authorization")
//    public List<Plato> buscarPlato(@PathVariable String idRest){
//        return this.platosService.buscarPlato(idRest);
//    }

    @GetMapping("/restaurante/{idRest}/platos/{categoria}")
    @SecurityRequirement(name = "authorization")
    public List<Plato> filtrarPlatos(@PathVariable String idRest, @PathVariable int categoria) {
        return this.platosService.filtrarPlatos(idRest, categoria);
    }

    @PostMapping("/restaurante/{idRest}/platos")
    @SecurityRequirement(name = "authorization")
    public void insertarPlato(@PathVariable String idRest,@RequestParam String nombre, @RequestParam int categoria, @RequestParam double precio) {
        this.platosService.insertarPlato(idRest, nombre, categoria, precio);
    }

    @DeleteMapping("/restaurante/{_idRest}/platos/{idPlato}")
    @SecurityRequirement(name = "authorization")
    public void borrarPlato(@PathVariable String _idRest, @PathVariable String idPlato) {
        this.platosService.borrarPlato(idPlato);
    }

    @PostMapping("/pedidos/{idPedido}/estado")
    @SecurityRequirement(name = "authorization")
    public void cambiarEstadoPedido(@PathVariable String idPedido, @RequestParam int estado) {
        this.pedidosService.cambiarEstado(idPedido, estado);
    }

    @PostMapping("/restaurante/{idRest}/platos/{idPlato}/rebaja")
    @SecurityRequirement(name = "authorization")
    public void establecerRebaja(@PathVariable String idRest, @PathVariable String idPlato, @RequestParam double nuevoPrecio, @RequestParam String fecha) {
        this.platosService.establecerRebaja(idRest, idPlato, nuevoPrecio, fecha);
    }
}
