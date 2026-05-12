package es.grupoO.FastFood.services;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.PedidosRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {
    private PedidosRepository repository;

    public Pedido realizarPedido(ObjectId idCliente, ObjectId idRest, List<Pair<ObjectId, Integer>> platos) {
        // TODO
        return null;
    }

    public Pedido buscarPedidoPorID(ObjectId idPedido) {
        return this.repository.findById(idPedido).get();
    }
    
    public void anularPedido(ObjectId idPedido) {
        this.repository.deleteById(idPedido);
    }
    
    public void cambiarEstado(ObjectId idPedido, int estado) {
        // TODO
    }
    
    public void asignarPedido(ObjectId idPedido, ObjectId idRepartidor) {
        // TODO
    }
    
    public List<Pedido> buscarPedidosARepartir(long ubicacionRepartidor) {
        // TODO
        return null;
    }
}
