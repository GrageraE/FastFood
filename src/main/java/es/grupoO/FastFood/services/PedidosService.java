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
import es.grupoO.FastFood.exceptions.NoExistDBException;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private RepartidoresService repartidoresService;

    @Autowired
    private LineaPlatosRepository lineaPlatosRepository;

    public Pedido realizarPedido(String idCliente, String idRest, List<Pair<String, Integer>> platos) {
        PedidosFactory  pedidosFactory = new PedidosFactory(idCliente, idRest, platos);

        Pedido pedido = pedidosFactory.fabricarPedido();

        for(LineaPlatos linea : pedido.getPlatos()) {
            this.lineaPlatosRepository.save(linea);
        }
        this.pedidosRepository.save(pedido);

        return pedido;
    }

    public Pedido buscarPedidoPorID(String idPedido) {
        Pedido pedido = this.pedidosRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }
        return pedido;
    }
    
    public void anularPedido(String idPedido) {
        Pedido pedido = this.pedidosRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }

        for(LineaPlatos linea : pedido.getPlatos()) {
            this.lineaPlatosRepository.deleteById(linea.getId());
        }

        this.pedidosRepository.deleteById(idPedido);
    }
    
    public void cambiarEstado(String idPedido, int estado) {
        Pedido pedido = this.pedidosRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }
        EstadoPedido estadoPedido = EstadoPedido.values()[estado];
        pedido.setEstado(estadoPedido);
    }
    
    public void asignarPedido(String idPedido, String idRepartidor) {
        // TODO: Errores
        Pedido pedido = this.buscarPedidoPorID(idPedido);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }
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
