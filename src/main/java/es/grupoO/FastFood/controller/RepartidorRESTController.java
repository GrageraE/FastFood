package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RepartidorRESTController {
    @Autowired
    private final PedidosService pedidosService;
    @Autowired
    private final RepartidoresService repartidoresService;

    public RepartidorRESTController(PedidosService pedidosService, RepartidoresService repartidoresService){
        this.pedidosService = pedidosService;
        this.repartidoresService = repartidoresService;
    }

    @PostMapping("/repartidores/validar")
    public Repartidor validar(@RequestParam String email, @RequestParam String passwd) {
        return this.repartidoresService.validar(email, passwd);
    }

    @PostMapping("/repartidores/registro")
    public void insertarRepartidor(@RequestParam String nombre, @RequestParam String telefono,
                                   @RequestParam String email, @RequestParam String passwd) {
        this.repartidoresService.insertarRepartidor(nombre, telefono, email, passwd);
    }

    @GetMapping("/repartidor/{idRepar}")
    @SecurityRequirement(name = "authorization")
    public Repartidor buscarRepartidorPorID(@PathVariable ObjectId idRepar){
        return this.repartidoresService.buscarRepartidorPorID(idRepar);
    }

    @DeleteMapping("/repartidor/{idRepar}")
    @SecurityRequirement(name = "authorization")
    public void borrarRepartidor(@PathVariable ObjectId idRepar) {
        this.repartidoresService.borrarRepartidor(idRepar);
    }

    @GetMapping("/pedidos/disponibles")
    @SecurityRequirement(name = "authorization")
    public List<Pedido> buscarPedidosRepartir(@RequestParam long ubicacion) {
        // TODO: Cambiar tipo de parametro a GeoJSON
        return this.pedidosService.buscarPedidosARepartir(ubicacion);
    }

    @PostMapping("/pedidos/{idPedido}/asignar")
    @SecurityRequirement(name = "authorization")
    public void asignarPedido(@PathVariable ObjectId idPedido, @RequestParam ObjectId idRepartidor) {
        this.pedidosService.asignarPedido(idPedido, idRepartidor);
    }
}
