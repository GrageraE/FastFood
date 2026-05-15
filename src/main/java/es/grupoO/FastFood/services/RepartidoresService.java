package es.grupoO.FastFood.services;

import es.grupoO.FastFood.dto.RepartidorLoginDTO;
import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.exceptions.UsernameAlreadyExistException;
import es.grupoO.FastFood.factory.RepartidorFactory;
import es.grupoO.FastFood.mapper.RepartidorLoginMapper;
import es.grupoO.FastFood.model.valueobject.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.repository.RepartidoresRepository;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.auth.HashMaker;

@Service
public class RepartidoresService {
    @Autowired
    private RepartidoresRepository repository;
    @Autowired
    private AuthService authService;
    @Autowired
    private RepartidorLoginMapper repartidorMapper;

    public RepartidorLoginDTO validar(String email, String passwd) {
        Pair<Repartidor, String> data = this.authService.loginRepartidor(email, passwd);
        return this.repartidorMapper.fromPair(data);
    }

    public Repartidor buscarRepartidorPorID(String idRepar) {
        Repartidor repartidor = this.repository.findById(idRepar).orElse(null);
        if (repartidor == null) {
            throw new NoExistDBException("El repartidor no esta registrado");
        }
        return repartidor;
    }

    public Repartidor insertarRepartidor(String nombre, String telefono, String email, String passwd) {
        if(this.repository.findByEmail(Email.parse(email)) == null) {
            throw new UsernameAlreadyExistException("El repartidor ya existe");
        }
        
        RepartidorFactory repartidorFactory = new RepartidorFactory(nombre, telefono, email, passwd);
        Repartidor repartidor = repartidorFactory.fabricarRepartidor();
        
        this.repository.save(repartidor);
        return repartidor;
    }

    public void borrarRepartidor(String idRepar) {
        Repartidor repartidor = this.repository.findById(idRepar).orElse(null);
        if (repartidor == null) {
            throw new NoExistDBException("El repartidor no esta registrado");
        }
        this.repository.deleteById(idRepar);
    }

    public void passwdChanger(String idRepartidor, String newPasswd) {
        Repartidor repartidor = this.buscarRepartidorPorID(idRepartidor);
        if(repartidor == null) {
            throw new NoExistDBException("El repartidor no esta registrado");
        }
        HashMaker hasher = new HashMaker();
        repartidor.setHashPassword(hasher.encoder(newPasswd));

        this.repository.save(repartidor);
    }
}
