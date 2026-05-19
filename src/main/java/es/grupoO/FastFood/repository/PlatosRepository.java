package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Plato;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import es.grupoO.FastFood.model.state.CategoriaPlato;

import java.util.List;

public interface PlatosRepository extends MongoRepository<Plato, String> {
    @Query("{ 'restaurante.$id': ?0 }")
    List<Plato> findAllByRestaurante(ObjectId idRestaurante);

    default List<Plato> findAllByRestaurante(String idRestaurante) {
        return this.findAllByRestaurante(new ObjectId(idRestaurante));
    }

    @Query("{ 'restaurante.$id': ?0, 'categoriaPlato': ?1 }")
    List<Plato> findAllByRestauranteIdAndCategoria(ObjectId idRestaurante, CategoriaPlato categoria);

    default List<Plato> findAllByRestauranteIdAndCategoria(String idRestaurante, CategoriaPlato categoria) {
        return this.findAllByRestauranteIdAndCategoria(new ObjectId(idRestaurante), categoria);
    }
    
}
