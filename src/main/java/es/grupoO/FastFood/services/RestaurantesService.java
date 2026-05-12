package es.grupoO.FastFood.services;
import es.grupoO.FastFood.factory.RestauranteFactory;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Valoracion;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import es.grupoO.FastFood.repository.ValoracionesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantesService {
    @Autowired
    private RestaurantesRepository repository;
    @Autowired
    private ValoracionesRepository valoracionesRepository;

    public Restaurante validar(String email, String passwd) {
        // TODO
        return null;
    }

    public Restaurante buscarRestaurantePorID(ObjectId idRest) {
        return this.repository.findById(idRest).get();
    }

    public List<Restaurante> buscarRestaurante(String nombre) {
        return this.repository.findAllByNombreContaining(nombre);
    }

    public Restaurante insertarRestaurante(String nombre, int categoria, String direccion,
                                    String telefono, String email, String horaApertura,
                                    String horaCierre, String passwd)
    {
        RestauranteFactory fact = new RestauranteFactory(nombre, direccion, telefono, horaApertura, horaCierre, categoria, email, passwd);
        Restaurante restaurante = fact.fabricarRestaurante();
        Valoracion val = restaurante.getValoracion();
        this.valoracionesRepository.save(val);
        this.repository.save(restaurante);
        return restaurante;
    }

    public void borrarRestaurante(ObjectId id) {
        this.repository.deleteById(id);
    }

    public void actualizarValoracion(ObjectId id, int valor) {
        // TODO
    }

    public void passwdChanger(ObjectId idCliente, String newPasswd) {
        //TODO
    }
}
