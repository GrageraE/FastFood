package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.dto.ClienteInsertDTO;
import es.grupoO.FastFood.dto.FormLoginDTO;
import es.grupoO.FastFood.dto.PedidoRequestDTO;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import es.grupoO.FastFood.dto.ClienteLoginDTO;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/cliente/validar")
    public ClienteLoginDTO validar(@RequestBody FormLoginDTO form) {
        String email = form.getEmail();
        String passwd = form.getPasswd();
        return this.clientesService.validar(email, passwd);
    }

    @PostMapping("/cliente/registro")
    public Cliente insertarCliente(@RequestBody ClienteInsertDTO data) {
        String nombre = data.getNombre();
        String direccion = data.getDireccion();
        String telefono = data.getTelefono();
        String email = data.getEmail();
        String passwd = data.getPasswd();
        return this.clientesService.insertarCliente(nombre, direccion, telefono, email, passwd);
    }

    @GetMapping("/cliente/{idCliente}")
    @SecurityRequirement(name = "authorization")
    public Cliente buscarClientePorID(@PathVariable String idCliente) {
        return this.clientesService.buscarClientePorID(idCliente);
    }

    @GetMapping("/restaurante/buscar")
    @SecurityRequirement(name = "authorization")
    public Page<Restaurante> buscarRestaurante(@RequestParam String nombre,
                                               @RequestParam(required = false, defaultValue = "0") int pagina,
                                               @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable paginacion = PageRequest.of(pagina, size, Sort.by("nombre").ascending());
        return this.restaurantesService.buscarRestaurante(nombre, paginacion);
    }

    @GetMapping("/restaurante/buscarCategoria")
    @SecurityRequirement(name = "authorization")
    public Page<Restaurante> buscarRestauranteCat(@RequestParam int categoria,
                                                  @RequestParam(required = false, defaultValue = "0") int pagina,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable paginacion = PageRequest.of(pagina, size, Sort.by("nombre").ascending());
        return this.restaurantesService.buscarRestaurante(categoria, paginacion);
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
    public Pedido realizarPedido(@RequestBody PedidoRequestDTO pedidoRequest) {
        String idCliente = pedidoRequest.getIdCliente();
        String idRest = pedidoRequest.getIdRest();
        var platos = pedidoRequest.getPlatos();
        return this.pedidosService.realizarPedido(idCliente, idRest, platos);
    }

    @PostMapping("/pagos")
    @SecurityRequirement(name = "authorization")
    public void realizarPago(@RequestBody double cantidad) {
        this.pagosService.realizarPago(cantidad);
    }

    @PutMapping("/cliente/password")
    @SecurityRequirement(name = "authorization")
    public void changePasswdCliente(@RequestBody String newPasswd,
                            Authentication auth) {
        this.clientesService.changePasswdCliente(newPasswd, auth);
    }

    @DeleteMapping("/cliente/{idCliente}")
    @SecurityRequirement(name = "authorization")
    public void eliminarCliente(Authentication auth) {
        this.clientesService.eliminarCliente(idCliente);
    }
}
