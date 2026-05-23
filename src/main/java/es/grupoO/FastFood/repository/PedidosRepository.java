package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.state.EstadoPedido;
import es.grupoO.FastFood.model.valueobject.Posicion;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PedidosRepository extends MongoRepository<Pedido, String> {

    /**
     *
     * @param clienteId
     * @return Lista de pedidos realizados por un cliente específico, identificados por su ID.
     */
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

    /**
     *
     * @param estadoPedido
     * @param poscionRepartidor
     * @return Hace una consulta de un pedido y pide la localizacion del repartidor, devuele
     * un pageable de pedidos con el estado listo para entregar, y la localizacion del repartidor,
     * para que el repartidor pueda ver los pedidos que tiene cerca de su localizacion
     */

    @Query("{'estadoPedido': ?0}")
    Page<Pedido> findAllByEstadoPedido(EstadoPedido estado, Pageable pageable);

    Page<Pedido> findByEstadoPedidoAndPosicionNear(EstadoPedido estado, GeoJsonPoint posicion, Distance distance, Pageable pageable);

    default Page<Pedido> buscarPorUbicacion(Posicion posRepartidor, Pageable pageable) {
        Distance distancia = new Distance(100, Metrics.KILOMETERS);
        return this.findByEstadoPedidoAndPosicionNear(
                EstadoPedido.LISTO_PARA_ENTREGAR, posRepartidor.toGeoJson(), distancia, pageable);
    }
}
