package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ClientesRepository extends MongoRepository<Cliente, String> {
    @Query("{email: '?0'}")
    Cliente findByEmail(Email email);
}
