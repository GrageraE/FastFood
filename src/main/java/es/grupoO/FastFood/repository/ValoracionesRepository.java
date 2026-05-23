package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Valoracion;
import org.springframework.data.mongodb.repository.MongoRepository;
//Fabricacion pura entre servicios y bases de datos
public interface ValoracionesRepository extends MongoRepository<Valoracion, String> {
}
