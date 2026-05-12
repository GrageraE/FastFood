package es.grupoO.FastFood.services;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.repository.RepartidoresRepository;


@Service
public class RepartidoresService {

    private RepartidoresRepository repository;

    public Repartidor validar(String email, String passwd){
        // TODO
        return null;
    }

    public Repartidor buscarRepartidorPorID(long idRepar){
        return this.repository.findById(idRepar).get();
    }

    public void insertarRepartidor(String nombre, String telefono,
                                        String email, String passwd){
        // TODO
    }

    public void borrarRepartidor(long idRepar) {
            this.repository.deleteById(idRepar);
    }

}
