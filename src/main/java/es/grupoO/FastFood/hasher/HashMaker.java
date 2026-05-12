package es.grupoO.FastFood.hasher;

import es.grupoO.FastFood.services.ClientesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import es.grupoO.FastFood.model.entity.Cliente;

@Component
public final class HashMaker {
    @Autowired
    private ClientesService clientesService;
    private BCryptPasswordEncoder encoder;

    public HashMaker() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String encoder(String password){
        return this.encoder.encode(password);
    }

    public Boolean verifier(ObjectId idCliente, String password){
        Cliente cliente = this.clientesService.buscarClientePorID(idCliente);
        return this.encoder.matches(password, cliente.gethashPassword());
    }

}
