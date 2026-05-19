package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import es.grupoO.FastFood.dto.RepartidorLoginDTO;

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

    @PostMapping("/repartidor/validar")
    public RepartidorLoginDTO validar(@RequestBody String email, @RequestBody String passwd) {
        return this.repartidoresService.validar(email, passwd);
    }

    @PostMapping("/repartidor/registro")
    public Repartidor insertarRepartidor(@RequestBody String nombre, @RequestBody String telefono,
                                   @RequestBody String email, @RequestBody String passwd) {
        return this.repartidoresService.insertarRepartidor(nombre, telefono, email, passwd);
    }

    @GetMapping("/repartidor/{idRepar}")
    @SecurityRequirement(name = "authorization")
    public Repartidor buscarRepartidorPorID(@PathVariable String idRepar){
        return this.repartidoresService.buscarRepartidorPorID(idRepar);
    }

    @DeleteMapping("/repartidor/{idRepar}")
    @SecurityRequirement(name = "authorization")
    public void borrarRepartidor(@PathVariable String idRepar) {
        this.repartidoresService.borrarRepartidor(idRepar);
    }

    @GetMapping("/pedidos/disponibles")
    @SecurityRequirement(name = "authorization")
    public List<Pedido> buscarPedidosRepartir(@RequestBody long ubicacion) {
        // TODO: Cambiar tipo de parametro a GeoJSON
        return this.pedidosService.buscarPedidosARepartir(ubicacion);
    }

    @PostMapping("/pedidos/{idPedido}/asignar")
    @SecurityRequirement(name = "authorization")
    public void asignarPedido(@PathVariable String idPedido, @RequestBody String idRepartidor) {
        this.pedidosService.asignarPedido(idPedido, idRepartidor);
    }

    @PutMapping("/repartidor/password")
    @SecurityRequirement(name = "authorization")
    public void changePasswdRepartidor(@RequestBody String newPasswd,
                            Authentication auth) {
        this.repartidoresService.changePasswdRepartidor(newPasswd, auth);
    }
}
