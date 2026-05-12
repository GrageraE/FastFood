package es.grupoO.FastFood.controller;

import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import es.grupoO.FastFood.services.RestaurantesService;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.services.PedidosService;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Plato;
import java.util.List;

@RestController
public class RestauranteRESTController {

    private final RestaurantesService restaurantesService;
    private final PlatosService platosService;
    private final PedidosService pedidosService;

    public RestauranteRESTController(RestaurantesService restaurantesService, PlatosService platosService, PedidosService pedidosService) {
        this.restaurantesService = restaurantesService;
        this.platosService = platosService;
        this.pedidosService = pedidosService;
    }

    @PostMapping("/restaurantes/validar")
    public Restaurante validarRestaurante(
            @RequestParam String email,
            @RequestParam String password) {

        return restaurantesService.validar(email, password);
    }

    @PostMapping("/buscarRestaurante")
    public Restaurante buscarRestaurantePorID(@RequestParam ObjectId idRest) {

        return this.restaurantesService.buscarRestaurantePorID(idRest);
    }

    @PostMapping("/restaurantes/register")
    public void insertarRestaurante(@RequestParam String nombre,@RequestParam  int categoria,@RequestParam  String direccion,
                                    @RequestParam String telefono,@RequestParam  String email,@RequestParam  String horaApertura,
                                    @RequestParam String horaCierre,@RequestParam  String passwd)
    {
        this.restaurantesService.insertarRestaurante(nombre, categoria, direccion, telefono, email, horaApertura, horaCierre, passwd);
    }

    @DeleteMapping("/restaurantes/{idRest}")
    public void borrarRestaurante(@PathVariable ObjectId idRest) {
        this.restaurantesService.borrarRestaurante(idRest);
    }

    @GetMapping("/restaurantes/{idRest}/platos")
    public List<Plato> buscarPlato(@PathVariable ObjectId idRest){
        return this.platosService.buscarPlato(idRest);
    }

    @GetMapping("/restaurantes/{idRest}/platos/{categoria}")
    public List<Plato> filtrarPlatos(@PathVariable ObjectId idRest, @PathVariable int categoria) {
        return this.platosService.filtrarPlatos(idRest, categoria);
    }

    @PostMapping("/restaurantes/{idRest}/platos")
    public void insertarPlato(@PathVariable ObjectId idRest,@RequestParam String nombre, @RequestParam int categoria, @RequestParam double precio) {
        this.platosService.insertarPlato(idRest, nombre, categoria, precio);
    }

    @DeleteMapping("/restaurantes/{_idRest}/platos/{idPlato}")
    public void borrarPlato(@PathVariable ObjectId _idRest, @PathVariable ObjectId idPlato) {
        this.platosService.borrarPlato(idPlato);
    }

    @PostMapping("/pedidos/{idPedido}/entregar")
    public void cambiarEstadoPedido(@PathVariable ObjectId idPedido, @RequestParam int estado) {
        this.pedidosService.cambiarEstado(idPedido, estado);
        }
    
    @PostMapping("/restaurantes/{idRest}/platos/{idPlato}/rebaja")
    public void establecerRebaja(@PathVariable ObjectId idRest, @PathVariable ObjectId idPlato, @RequestParam double nuevoPrecio, @RequestParam String fecha) {
        this.platosService.establecerRebaja(idRest, idPlato, nuevoPrecio, fecha);
    }
}
