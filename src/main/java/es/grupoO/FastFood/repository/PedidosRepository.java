package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.EstadoPedido;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PedidosRepository extends MongoRepository<Pedido, String> {
    @Query("{'cliente.$id': ?0}")
    List<Pedido> findAllByClienteId(ObjectId clienteId);

    default List<Pedido> findAllByClienteId(String clienteId) {
        return this.findAllByClienteId(new ObjectId(clienteId));
    }

    @Query("{'restaurante': ?0}")
    List<Pedido> findAllByRestauranteId(ObjectId restauranteId);

    default List<Pedido> findAllByRestauranteId(String restauranteId) {
        return this.findAllByRestauranteId(new ObjectId(restauranteId));
    }

    @Query("{'estadoPedido': ?0}")
    List<Pedido> findAllByEstadoPedido(EstadoPedido estado);
}