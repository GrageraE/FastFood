package es.grupoO.FastFood.services;

import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.repository.PlatosRepository;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class PlatosService {
    private RestaurantesRepository restRepo;
    private PlatosRepository repository;

    public Plato buscarPlatoPorID(long platoId) {
        return this.repository.findById(platoId).get();
    }

    public List<Plato> buscarPlato(long idRestaurante) {
        Restaurante rest = this.restRepo.findById(idRestaurante).get();
        return this.repository.findAllByRestauranteIdRestaurante(rest);
    }

    public List<Plato> filtrarPlatos(long idRestaurante, int categoria) {
        Restaurante rest = this.restRepo.findById(idRestaurante).get();
        
        // TODO: Construir la categoria
//        return this.repository.findAllByRestauranteIdRestauranteAndTipoPlato(rest, categoria);
        return null;
    }
    
    public void insertarPlato(long idRest, String nombre, int categoria, double precio) {
        // TODO: Construir el plato
    }
    
    public void borrarPlato(long idPlato) {
        this.repository.deleteById(idPlato);
    }
    
    public void establecerRebaja(long idRest, long idPlato, double nuevoPrecio, LocalTime fechaFin) {
        // TODO
    }
}
