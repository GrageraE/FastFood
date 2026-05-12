package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Email;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

public interface ClientesRepository extends MongoRepository<Cliente, ObjectId> {
    @Query("{email: '?0'}")
    Cliente findByEmail(Email email);
}
