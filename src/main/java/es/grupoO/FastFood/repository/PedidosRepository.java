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
    @Query("{'cliente.$id': ?0}")
    List<Pedido> findAllByClienteId(ObjectId clienteId);

    default List<Pedido> findAllByClienteId(String clienteId) {
        return this.findAllByClienteId(new ObjectId(clienteId));
    }

    @Query("{'cliente.$id': ?0}")
    Page<Pedido> findAllByClienteIdPage(ObjectId clienteId, Pageable pageable);

    /**
     * Encuentra todos los pedidos realizados por el cliente dado
     * @param clienteId El ID del cliente a buscar
     * @param pageable Paginacion
     * @return Pedidos realizados por el cliente dado
     */
    default Page<Pedido> buscarPorCliente(String clienteId, Pageable pageable) {
        return this.findAllByClienteIdPage(new ObjectId(clienteId), pageable);
    }

    Page<Pedido> findByEstadoPedidoAndPosicionNear(EstadoPedido estado, GeoJsonPoint posicion, Distance distance, Pageable pageable);

    /**
     * Busca los pedidos listos para entregar que procedan de los restaurantes mas cercanos a la posicion dada
     * @param posRepartidor La posicion actual del repartidor
     * @param pageable Paginacion
     * @return Pedidos con el estado listo para entregar
     */
    default Page<Pedido> buscarPorUbicacion(Posicion posRepartidor, Pageable pageable) {
        Distance distancia = new Distance(100, Metrics.KILOMETERS);
        return this.findByEstadoPedidoAndPosicionNear(
                EstadoPedido.LISTO_PARA_ENTREGAR, posRepartidor.toGeoJson(), distancia, pageable);
    }
}
