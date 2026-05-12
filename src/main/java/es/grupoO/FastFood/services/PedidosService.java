package es.grupoO.FastFood.services;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.PedidosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidosService {
    private PedidosRepository repository;

    public Pedido realizarPedido(long idCliente, long idRest, List<Pair<Long, Integer>> platos) {
        // TODO
        return null;
    }

    public Pedido buscarPedidoPorID(long idPedido) {
        return this.repository.findById(idPedido).get();
    }
    
    public void anularPedido(long idPedido) {
        this.repository.deleteById(idPedido);
    }
    
    public void cambiarEstado(long idPedido, int estado) {
        // TODO
    }
    
    public void asignarPedido(long idPedido, long idRepartidor) {
        // TODO
    }
    
    public List<Pedido> buscarPedidosARepartir(long ubicacionRepartidor) {
        // TODO
        return null;
    }
}
