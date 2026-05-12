package es.grupoO.FastFood.controller;

import es.grupoO.FastFood.services.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RepartidorRESTController {
    private final PedidosService pedidosService;
    private final RepartidoresService repartidoresService;

    public RepartidorRESTController(PedidosService pedidosService, RepartidoresService repartidoresService){
        this.pedidosService = pedidosService;
        this.repartidoresService = repartidoresService;
    }

    public void insertarRepartidor(String nombre, String telefono, String email, String passwd){
        this.repartidoresService.insertarRepartidor(nombre, telefono, email, passwd);
    }

    public void buscarRepartidorPorID(long idRepar){
        this.repartidoresService.buscarRepartidorPorID(idRepar);
    }

    public void borrarRepartidor(long idRepar) {
        this.repartidoresService.borrarRepartidor(idRepar);
    }

    /*

    public Cliente buscarClientePorID(long idCliente) {
        return this.clientesService.buscarClientePorID(idCliente);        
    }

    public List<Restaurante> buscarRestaurante(String nombre) {
        return this.restaurantesService.buscarRestaurante(nombre);
    }

    public List<Plato> buscarPlato(long idRestaurante) {
        return this.repository.findAllByRestauranteIdRestaurante(rest);
    }
    
    public void actualizarValoracion(long id, int valor) {
        this.restaurantesService.actualizarValoracion(id, valor);
    }
    */
}
