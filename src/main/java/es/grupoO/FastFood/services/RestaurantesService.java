package es.grupoO.FastFood.services;
import es.grupoO.FastFood.factory.RestauranteFactory;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Valoracion;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import es.grupoO.FastFood.repository.ValoracionesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import es.grupoO.FastFood.hasher.HashMaker;


@Service
public class RestaurantesService {
    @Autowired
    private RestaurantesRepository repository;
    @Autowired
    private ValoracionesRepository valoracionesRepository;

    public Restaurante validar(String email, String passwd) {
        //TODO puede petar
        Restaurante rest = this.repository.findByEmail(Email.parse(email));
        if(rest == null) {
            // TODO
            return null;
        }

        HashMaker hasher = new HashMaker();
        if(!hasher.verify(rest.gethashPassword(), passwd)) {
            //TODO   
            return null;
        }
        return rest;
    }

    public Restaurante buscarRestaurantePorID(ObjectId idRest) {
        //TODO puede petar
        return this.repository.findById(idRest).get();
    }

    public List<Restaurante> buscarRestaurante(String nombre) {
        //TODO puede petar
        return this.repository.findAllByNombreContaining(nombre);
    }

    public Restaurante insertarRestaurante(String nombre, int categoria, String direccion,String telefono, 
        String email, String horaApertura, String horaCierre, String passwd){
            //TODO comprobar condiciones de insercion
        RestauranteFactory fact = new RestauranteFactory(nombre, direccion, telefono, horaApertura, horaCierre, categoria, email, passwd);
        Restaurante restaurante = fact.fabricarRestaurante();
        Valoracion val = restaurante.getValoracion();
        this.valoracionesRepository.save(val);
        this.repository.save(restaurante);
        return restaurante;
    }

    public void borrarRestaurante(ObjectId id) {
        //TODO puede petar
        this.repository.deleteById(id);
    }

    public void actualizarValoracion(ObjectId id, int valor) {
        //TODO puede petar 
        Restaurante restaurante = this.buscarRestaurantePorID(id);
        if(restaurante == null) {
            // TODO: Error
        }
        
        Valoracion valoracion = restaurante.getValoracion();
        valoracion.actualizarValoracion(valor);
        
        this.valoracionesRepository.save(valoracion);
        
    }

    public void passwdChanger(ObjectId idRest, String newPasswd) {
        //TODO comprobar que el usuario cambia su propia contraseña
        //TODO puede petar si el rest no existe
        Restaurante rest = this.buscarRestaurantePorID(idRest);
        HashMaker hasher = new HashMaker();
        rest.setHashPassword(hasher.encoder(newPasswd));

        this.repository.save(rest);
    }
}
