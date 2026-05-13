package es.grupoO.FastFood.services;
import es.grupoO.FastFood.factory.RepartidorFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.repository.RepartidoresRepository;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.hasher.HashMaker;


@Service
public class RepartidoresService {
    @Autowired
    private RepartidoresRepository repository;

    public Repartidor validar(String email, String passwd) {
        // TODO: puede petar 
        Repartidor repartidor = this.repository.findByEmail(Email.parse(email));
        HashMaker hasher = new HashMaker();
        if(!hasher.verify(repartidor.getHashPassword(), passwd)) {
         // TODO: verificar   
         return null;
        } else {
            return repartidor;
        }
    }

    public Repartidor buscarRepartidorPorID(ObjectId idRepar){
        // TODO: Puede petar
        return this.repository.findById(idRepar).get();
    }

    public void insertarRepartidor(String nombre, String telefono, String email, String passwd) {
        //TODO comprobar condicione de insertar
        RepartidorFactory repartidorFactory = new RepartidorFactory(nombre, telefono, email, passwd);
        Repartidor repartidor = repartidorFactory.fabricarRepartidor();
        
        this.repository.save(repartidor);
    }

    public void borrarRepartidor(ObjectId idRepar) {
        //TODO puede petar
        this.repository.deleteById(idRepar);
    }

    public void passwdChanger(ObjectId idRepartidor, String newPasswd) {
        //TODO puede petar
        Repartidor repartidor = this.buscarRepartidorPorID(idRepartidor);
        HashMaker hasher = new HashMaker();
        repartidor.setHashPassword(hasher.encoder(newPasswd));

        this.repository.save(repartidor);    }
}
