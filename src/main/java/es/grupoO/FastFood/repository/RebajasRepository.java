package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Rebaja;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RebajasRepository extends MongoRepository<Rebaja, String> {

}
