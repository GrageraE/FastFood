package es.grupoO.FastFood.services;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.repository.RepartidoresRepository;


@Service
public class RepartidoresService {

    private RepartidoresRepository repository;

    public Repartidor validar(String email, String passwd) {
        // TODO
        return null;
    }

    public Repartidor buscarRepartidorPorID(ObjectId idRepar){
        return this.repository.findById(idRepar).get();
    }

    public void insertarRepartidor(String nombre, String telefono,
                                        String email, String passwd){
        // TODO
    }

    public void borrarRepartidor(ObjectId idRepar) {
            this.repository.deleteById(idRepar);
    }

    public void passwdChanger(ObjectId idCliente, String newPasswd) {
        //TODO
    }
}
