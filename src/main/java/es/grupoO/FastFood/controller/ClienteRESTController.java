package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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
    @SecurityRequirement(name = "authorization")
    public Cliente buscarClientePorID(@PathVariable String idCliente) {
        return this.clientesService.buscarClientePorID(idCliente);
    }

    @GetMapping("/restaurantes/buscar")
    @SecurityRequirement(name = "authorization")
    public List<Restaurante> buscarRestaurante(@RequestParam String nombre) {
        return this.restaurantesService.buscarRestaurante(nombre);
    }

    @GetMapping("/restaurante/{idRestaurante}/platos")
    @SecurityRequirement(name = "authorization")
    public List<Plato> buscarPlato(@PathVariable String idRestaurante) {
        return this.platosService.buscarPlato(idRestaurante);
    }

    @GetMapping("/restaurante/{idRestaurante}/platos/categorias/{categoria}")
    @SecurityRequirement(name = "authorization")
    public List<Plato> filtrarPlatos(@PathVariable String idRestaurante, @PathVariable int categoria) {
        return this.platosService.filtrarPlatos(idRestaurante, categoria);
    }

    @PostMapping("/restaurante/{id}/valoracion")
    @SecurityRequirement(name = "authorization")
    public void actualizarValoracion(@PathVariable String id, @RequestParam int valor) {
        this.restaurantesService.actualizarValoracion(id, valor);
    }

    @PostMapping("/pedidos")
    @SecurityRequirement(name = "authorization")
    public Pedido realizarPedido(@RequestParam String idCliente, @RequestParam String idRest,
                                 @RequestParam List<Pair<String, Integer>> platos) {
        return this.pedidosService.realizarPedido(idCliente, idRest, platos);
    }

    @PostMapping("/pagos")
    @SecurityRequirement(name = "authorization")
    public void realizarPago(@RequestParam double cantidad) {
        this.pagosService.realizarPago(cantidad);
    }
}
