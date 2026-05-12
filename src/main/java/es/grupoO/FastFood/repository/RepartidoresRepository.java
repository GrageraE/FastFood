package es.grupoO.FastFood.repository;

import es.grupoO.FastFood.model.entity.Repartidor;

import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RepartidoresRepository extends MongoRepository<Repartidor, Long> {
    @Query("{email: '?0'}")
    Repartidor findByEmail(Email email);

    @Query("{nombre: '?0'}")
    Repartidor findByNombre(String nombre);

    @Query("{telefono: '?0'}")
    Repartidor findByTelefono(String telefono);
}
