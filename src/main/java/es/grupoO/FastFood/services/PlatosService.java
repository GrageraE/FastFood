package es.grupoO.FastFood.services;

import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.repository.PlatosRepository;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatosService {
    private RestaurantesRepository restRepo;
    private PlatosRepository repository;

    public Plato buscarPlatoPorID(ObjectId platoId) {
        return this.repository.findById(platoId).get();
    }

    public List<Plato> buscarPlato(ObjectId idRestaurante) {
        Restaurante rest = this.restRepo.findById(idRestaurante).get();
        return this.repository.findAllByRestauranteIdRestaurante(rest);
    }

    public List<Plato> filtrarPlatos(ObjectId idRestaurante, int categoria) {
        Restaurante rest = this.restRepo.findById(idRestaurante).get();
        
        // TODO: Construir la categoria
//        return this.repository.findAllByRestauranteIdRestauranteAndTipoPlato(rest, categoria);
        return null;
    }
    
    public void insertarPlato(ObjectId idRest, String nombre, int categoria, double precio) {
        // TODO: Construir el plato
    }
    
    public void borrarPlato(ObjectId idPlato) {
        this.repository.deleteById(idPlato);
    }
    
    public void establecerRebaja(ObjectId idRest, ObjectId idPlato, double nuevoPrecio, String fechaFin) {
        // TODO
    }
}
