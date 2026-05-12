package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.services.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteRESTController {
    @Autowired
    private final ClientesService clientesService;

    @Autowired
    private final RestaurantesService restaurantesService;

    @Autowired
    private final PlatosService platosService;

    @Autowired
    private final PedidosService pedidosService;

    @Autowired
    private final PagosService pagosService;

    public ClienteRESTController(ClientesService clientesService, RestaurantesService restaurantesService,
                                 PlatosService platosService, PedidosService pedidosService, PagosService pagosService)
    {
        this.clientesService = clientesService;
        this.restaurantesService = restaurantesService;
        this.platosService = platosService;
        this.pedidosService = pedidosService;
        this.pagosService = pagosService;
    }

    @PostMapping("/clientes/validar")
    public Cliente validar(@RequestParam String email, @RequestParam String passwd) {
        return this.clientesService.validar(email, passwd);
    }

    @PostMapping("/clientes/registro")
    public void insertarCliente(@RequestParam String nombre, @RequestParam String direccion, @RequestParam String telefono, 
                                @RequestParam String email, @RequestParam String passwd) {
        this.clientesService.insertarCliente(nombre, direccion, telefono, email, passwd);
    }

    @GetMapping("/clientes/{idCliente}")
    public Cliente buscarClientePorID(@PathVariable ObjectId idCliente) {
        return this.clientesService.buscarClientePorID(idCliente);
    }

    @GetMapping("/restaurantes/buscar")
    public List<Restaurante> buscarRestaurante(@RequestParam String nombre) {
        return this.restaurantesService.buscarRestaurante(nombre);
    }

    @GetMapping("/restaurante/{idRestaurante}/platos")
    public List<Plato> buscarPlato(@PathVariable ObjectId idRestaurante) {
        return this.platosService.buscarPlato(idRestaurante);
    }

    @GetMapping("/restaurante/{idRestaurante}/platos/categorias/{categoria}")
    public List<Plato> filtrarPlatos(@PathVariable ObjectId idRestaurante, @PathVariable int categoria) {
        return this.platosService.filtrarPlatos(idRestaurante, categoria);
    }

    @PostMapping("/restaurante/{id}/valoracion")
    public void actualizarValoracion(@PathVariable ObjectId id, @RequestParam int valor) {
        this.restaurantesService.actualizarValoracion(id, valor);
    }

    @PostMapping("/pedidos")
    public Pedido realizarPedido(@RequestParam ObjectId idCliente, @RequestParam ObjectId idRest, 
                                 @RequestParam List<Pair<ObjectId, Integer>> platos) {
        return this.pedidosService.realizarPedido(idCliente, idRest, platos);
    }

    @PostMapping("/pagos")
    public void realizarPago(@RequestParam double cantidad) {
        this.pagosService.realizarPago(cantidad);
    }
}
