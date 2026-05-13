package es.grupoO.FastFood.services;

import es.grupoO.FastFood.model.entity.LineaPlatos;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.state.EstadoPedido;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.LineaPlatosRepository;
import es.grupoO.FastFood.repository.PedidosRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.factory.PedidosFactory;

import java.util.List;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private RepartidoresService repartidoresService;

    @Autowired
    private LineaPlatosRepository lineaPlatosRepository;

    public Pedido realizarPedido(ObjectId idCliente, ObjectId idRest, List<Pair<ObjectId, Integer>> platos) {
        PedidosFactory  pedidosFactory = new PedidosFactory(idCliente, idRest, platos);

        Pedido pedido = pedidosFactory.fabricarPedido();

        for(LineaPlatos linea : pedido.getPlatos()) {
            this.lineaPlatosRepository.save(linea);
        }
        this.pedidosRepository.save(pedido);

        return pedido;
    }

    public Pedido buscarPedidoPorID(ObjectId idPedido) {
        //TODO puede petar
        return this.pedidosRepository.findById(idPedido).get();
    }
    
    public void anularPedido(ObjectId idPedido) {
        // TODO: puede petar
        Pedido pedido = this.pedidosRepository.findById(idPedido).get();

        for(LineaPlatos linea : pedido.getPlatos()) {
            this.lineaPlatosRepository.deleteById(linea.getId());
        }

        this.pedidosRepository.deleteById(idPedido);
    }
    
    public void cambiarEstado(ObjectId idPedido, int estado) {
        // TODO puede petar
        Pedido pedido = this.pedidosRepository.findById(idPedido).get();
        // TODO: puede petar
        EstadoPedido estadoPedido = EstadoPedido.values()[estado];
        pedido.setEstado(estadoPedido);
    }
    
    public void asignarPedido(ObjectId idPedido, ObjectId idRepartidor) {
        // TODO: Errores
        Pedido pedido = this.buscarPedidoPorID(idPedido);
        Repartidor rep = this.repartidoresService.buscarRepartidorPorID(idRepartidor);

        if(!pedido.repartidorAsignado()) {
            pedido.asignarRepartidor(rep);
        } else {
            // TODO: Error
        }
    }
    
    public List<Pedido> buscarPedidosARepartir(long ubicacionRepartidor) {
        // TODO no sabemos ubicacion
        return null;
    }
}
