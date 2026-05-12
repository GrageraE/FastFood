package es.grupoO.FastFood.services;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestaurantesService {
    private RestaurantesRepository repository;

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

    public void insertarRestaurante(String nombre, int categoria, String direccion,
                                    String telefono, String email, String horaApertura,
                                    String horaCierre, String passwd)
    {
        // TODO
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
