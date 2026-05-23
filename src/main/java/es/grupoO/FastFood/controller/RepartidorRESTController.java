package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.dto.FormLoginDTO;
import es.grupoO.FastFood.dto.RepartidorInsertDTO;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.valueobject.Posicion;
import es.grupoO.FastFood.services.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import es.grupoO.FastFood.dto.RepartidorLoginDTO;

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
    public RepartidorLoginDTO validar(@RequestBody FormLoginDTO form) {
        String email = form.getEmail();
        String passwd = form.getPasswd();
        return this.repartidoresService.validar(email, passwd);
    }

    @PostMapping("/repartidor/registro")
    public Repartidor insertarRepartidor(@RequestBody RepartidorInsertDTO data) {
        String nombre = data.getNombre();
        String telefono = data.getTelefono();
        String email = data.getEmail();
        String passwd = data.getPassw();
        return this.repartidoresService.insertarRepartidor(nombre, telefono, email, passwd);
    }

    @GetMapping("/repartidor/{idRepar}")
    @SecurityRequirement(name = "authorization")
    public Repartidor buscarRepartidorPorID(@PathVariable String idRepar){
        return this.repartidoresService.buscarRepartidorPorID(idRepar);
    }

    @DeleteMapping("/repartidor/self")
    @SecurityRequirement(name = "authorization")
    public void borrarRepartidor(Authentication auth) {
        this.repartidoresService.borrarRepartidor(auth);
    }

    @GetMapping("/pedidos/disponibles")
    @SecurityRequirement(name = "authorization")
    public Page<Pedido> buscarPedidosRepartir(@ModelAttribute Posicion ubicacion,
                                              @RequestParam(required = false, defaultValue = "0") int pagina,
                                              @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable paginacion = PageRequest.of(pagina, size);
        return this.pedidosService.buscarPedidosARepartir(ubicacion, paginacion);
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
