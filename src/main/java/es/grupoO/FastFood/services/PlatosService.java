package es.grupoO.FastFood.services;

import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.factory.PlatosFactory;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Rebaja;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.CategoriaPlato;
import es.grupoO.FastFood.model.state.Divisa;
import es.grupoO.FastFood.model.valueobject.Precio;
import es.grupoO.FastFood.repository.PlatosRepository;
import es.grupoO.FastFood.repository.RebajasRepository;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlatosService {
    @Autowired
    private RestaurantesRepository restRepo;
    @Autowired
    private PlatosRepository platosRepository;
    @Autowired
    private RebajasRepository rebajasRepository;

    public Plato buscarPlatoPorID(ObjectId platoId) {
        return this.platosRepository.findById(platoId).orElse(null);
    }

    public List<Plato> buscarPlato(ObjectId idRestaurante) {
        Restaurante rest = this.restRepo.findById(idRestaurante).orElse(null);
        return this.platosRepository.findAllByRestauranteIdRestaurante(rest);
    }

    public List<Plato> filtrarPlatos(ObjectId idRestaurante, int categoria) {
        Restaurante rest = this.restRepo.findById(idRestaurante).orElse(null);

        // TODO: Revisar categoria
        CategoriaPlato cat = CategoriaPlato.values()[categoria];

        return this.platosRepository.findAllByRestauranteIdRestauranteAndTipoPlato(rest, cat);
    }
    
    public void insertarPlato(ObjectId idRest, String nombre, int categoria, double precio) {
        PlatosFactory platosFactory = new PlatosFactory(nombre, idRest, precio, categoria);
        Plato plato = platosFactory.fabricarPlato();
        this.platosRepository.save(plato);
    }
    
    public void borrarPlato(ObjectId idPlato) {
        this.platosRepository.deleteById(idPlato);
    }
    
    public void establecerRebaja(ObjectId _idRest, ObjectId idPlato, double nuevoPrecio, String fechaFin) {
        Precio pr = new Precio(nuevoPrecio, Divisa.EURO);
        LocalDate fecha = LocalDate.parse(fechaFin);
        Rebaja rebaja = new Rebaja(pr, fecha);
        
        Plato plato = this.buscarPlatoPorID(idPlato);
        if(plato == null) {
            throw new NoExistDBException("El plato no esta registrado");
        }
        
        plato.setRebaja(rebaja);
        
        this.rebajasRepository.save(rebaja);
        this.platosRepository.save(plato);
    }
}
