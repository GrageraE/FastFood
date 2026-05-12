package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.services.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClienteRESTController {
    private final ClientesService clientesService;

    private final RestaurantesService restaurantesService;

    private final PlatosService platosService;

    private final PedidosService pedidosService;

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
    public Cliente validar(String email, String passwd) {
        // TODO
        return this.clientesService.validar(email, passwd);
    }

    @PostMapping("/clientes/registro")
    public void insertarCliente(String nombre, String direccion, String telefono, String email, String passwd) {
        this.clientesService.insertarCliente(nombre, direccion, telefono, email, passwd);
    }

    @GetMapping("/clientes/{idCliente}")
    public Cliente buscarClientePorID(long idCliente) {
        return this.clientesService.buscarClientePorID(idCliente);
    }

    @PostMapping("/restaurantes/buscar")
    public List<Restaurante> buscarRestaurante(String nombre) {
        return this.restaurantesService.buscarRestaurante(nombre);
    }

    @GetMapping("/restaurante/{idRestaurante}/platos")
    public List<Plato> buscarPlato(long idRestaurante) {
        return this.platosService.buscarPlato(idRestaurante);
    }

    @GetMapping("/restaurante/{idRestaurante}/platos/categorias/{categoria}")
    public List<Plato> filtrarPlatos(long idRestaurante, int categoria) {
        return this.platosService.filtrarPlatos(idRestaurante, categoria);
    }

    @PostMapping("/restaurante/{id}/valoracion")
    public void actualizarValoracion(long id, int valor) {
        this.restaurantesService.actualizarValoracion(id, valor);
    }

    @PostMapping("/pedidos")
    public Pedido realizarPedido(long idCliente, long idRest, List<Pair<Long, Integer>> platos) {
        return this.pedidosService.realizarPedido(idCliente, idRest, platos);
    }

    @PostMapping("/pagos")
    public void realizarPago(double cantidad) {
        this.pagosService.realizarPago(cantidad);
    }
}
