package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.EstadoPedido;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PedidosRepository extends MongoRepository<Pedido, ObjectId> {
    @Query("{cliente: '?0'}")
    List<Pedido> findAllByClienteIdCliente(Cliente cliente);

    @Query("{restaurante: '?0'}")
    List<Pedido> findAllByRestauranteIdRestaurante(Restaurante rest);

    @Query("{estadoPedido: '?0'}")
    List<Pedido> findAllByEstadoPedido(EstadoPedido estado);
}