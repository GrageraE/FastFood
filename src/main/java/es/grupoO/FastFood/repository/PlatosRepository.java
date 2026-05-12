package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import es.grupoO.FastFood.model.state.CategoriaPlato;

import java.util.List;

public interface PlatosRepository extends MongoRepository<Plato, ObjectId> {
    @Query("{restaurante: '?0'}")
    List<Plato> findAllByRestauranteIdRestaurante(Restaurante rest);

    @Query("{restaurante: '?0', categoriaPlato: '?1'}")
    List<Plato> findAllByRestauranteIdRestauranteAndTipoPlato(Restaurante rest, CategoriaPlato categoria);
    
}
