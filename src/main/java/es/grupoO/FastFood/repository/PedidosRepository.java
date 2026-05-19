package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.state.EstadoPedido;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("{'cliente.$id': ?0}")
    Page<Pedido> findAllByClienteIdPage(ObjectId clienteId, Pageable pageable);

    default Page<Pedido> findAllByClienteIdPage(String clienteId, Pageable pageable) {
        return this.findAllByClienteIdPage(new ObjectId(clienteId), pageable);
    }

    @Query("{'restaurante': ?0}")
    Page<Pedido> findAllByRestauranteIdPage(ObjectId restauranteId, Pageable pageable);

    default Page<Pedido> findAllByRestauranteIdPage(String restauranteId, Pageable pageable) {
        return this.findAllByRestauranteIdPage(new ObjectId(restauranteId), pageable);
    }

    @Query("{'estadoPedido': ?0}")
    Page<Pedido> findAllByEstadoPedido(EstadoPedido estado, Pageable pageable);
}