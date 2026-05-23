package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.state.EstadoPedido;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.model.valueobject.Posicion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
//Fabricacion pura entre servicios y bases de datos
public interface RestaurantesRepository extends MongoRepository<Restaurante, String> {
    @Query("{'email': ?0}")
    Restaurante findByEmail(Email email);

    @Query("{'nombre': ?0}")
    List<Restaurante> findAllByNombreContaining(String nombre);

    @Query("{'categoria': ?0}")
    List<Restaurante> findAllByCategoria(CategoriaRestaurante categoria);

    @Query("{'nombre': ?0}")
    Page<Restaurante> findAllByNombreContainingPaginado(String nombre, Pageable pageable);

    @Query("{'categoria': ?0}")
    Page<Restaurante> findAllByCategoriaPaginado(CategoriaRestaurante categoria, Pageable pageable);


    /**
     *
     * @param posicion
     * @return Lista de restaurantes ordenados por proximidad
     */

    Page<Restaurante> findByPosicionNear(GeoJsonPoint posicion, Distance distance, Pageable pageable);

    default Page<Restaurante> findByPosicionNear(Posicion posCliente, Pageable pageable) {
        Distance distancia = new Distance(100, Metrics.KILOMETERS);
        return findByPosicionNear(posCliente.toGeoJson(), distancia, pageable);
    }
}

