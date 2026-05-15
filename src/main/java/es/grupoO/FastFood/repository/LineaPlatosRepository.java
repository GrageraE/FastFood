package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.LineaPlatos;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LineaPlatosRepository extends MongoRepository<LineaPlatos, String> {
}
