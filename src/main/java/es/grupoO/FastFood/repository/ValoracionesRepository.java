package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Valoracion;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ValoracionesRepository extends MongoRepository<Valoracion, ObjectId> {
}
