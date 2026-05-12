package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RepartidorRESTController {
    private final PedidosService pedidosService;
    private final RepartidoresService repartidoresService;

    public RepartidorRESTController(PedidosService pedidosService, RepartidoresService repartidoresService){
        this.pedidosService = pedidosService;
        this.repartidoresService = repartidoresService;
    }

    @PostMapping("/repartidores/validar")
    public Repartidor validar(@RequestParam String email, @RequestParam String passwd) {
        return this.repartidoresService.validar(email, passwd);
    }

    @PostMapping("/repartidores")
    public void insertarRepartidor(@RequestParam String nombre, @RequestParam String telefono,
                                   @RequestParam String email, @RequestParam String passwd) {
        this.repartidoresService.insertarRepartidor(nombre, telefono, email, passwd);
    }

    @GetMapping("/repartidor/{idRepar}")
    public Repartidor buscarRepartidorPorID(@PathVariable long idRepar){
        return this.repartidoresService.buscarRepartidorPorID(idRepar);
    }

    @DeleteMapping("/repartidor/{idRepar}")
    public void borrarRepartidor(@PathVariable long idRepar) {
        this.repartidoresService.borrarRepartidor(idRepar);
    }

    @GetMapping("/pedidos/disponibles")
    public List<Pedido> buscarPedidosRepartir(@RequestParam long ubicacion) {
        return this.pedidosService.buscarPedidosARepartir(ubicacion);
    }

    @PostMapping("/pedidos/{idPedido}/estado")
    public void cambiarEstadoPedido(@PathVariable long idPedido, @RequestParam int nuevoEstado) {
        this.pedidosService.cambiarEstado(idPedido, nuevoEstado);
    }

    @PostMapping("/pedidos/{idPedido}/asignar")
    public void asignarPedido(@PathVariable long idPedido, @RequestParam long idRepartidor) {
        this.pedidosService.asignarPedido(idPedido, idRepartidor);
    }
}
