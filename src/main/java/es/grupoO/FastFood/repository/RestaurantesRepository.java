package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface RestaurantesRepository extends MongoRepository<Restaurante, Long> {
    @Query("{email: '?0'}")
    Restaurante findByEmail(Email email);

    @Query("{nombre: '?0'}")
    List<Restaurante> findAllByNombreContaining(String nombre);
    
    @Query("{categoria: '?0'}")
    List<Restaurante> findAllByCategoria(CategoriaRestaurante categoria);
}
