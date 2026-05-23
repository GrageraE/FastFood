package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Rebaja;
import org.springframework.data.mongodb.repository.MongoRepository;
//Fabricacion pura entre servicios y bases de datos
public interface RebajasRepository extends MongoRepository<Rebaja, String> {

}
