package es.grupoO.FastFood.services;
import es.grupoO.FastFood.factory.ClienteFactory;
import es.grupoO.FastFood.hasher.HashMaker;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.repository.ClientesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {
    @Autowired
    ClientesRepository repository;
    
    public void insertarCliente(String nombre, String direccion, String telefono, String email, String passwd) {
        ClienteFactory clienteFactory = new ClienteFactory(nombre, direccion, telefono, email, passwd);
        Cliente cliente = clienteFactory.crearCliente();
        
        this.repository.save(cliente);
    }

    public Cliente validar(String email, String passwd) {
        Cliente cliente = this.repository.findByEmail(Email.parse(email));

        if(cliente == null) {
            // TODO
            return null;
        }

        HashMaker hasher = new HashMaker();
        if(!hasher.verify(cliente.gethashPassword(), passwd)) {
            //TODO   
            return null;
        }
        return cliente;
    }
    
    public Cliente buscarClientePorID(ObjectId idCliente) {
        // TODO: Que hacer si el ID no existe?
        return this.repository.findById(idCliente).get();
    }
    
    public void passwdChanger(ObjectId idCliente, String newPasswd) {
        //TODO comprobar que el usuario cambia su propia contraseña
        //TODO puede petar si el cliente no existe
        Cliente cliente = this.buscarClientePorID(idCliente);
        HashMaker hasher = new HashMaker();
        cliente.setHashPassword(hasher.encoder(newPasswd));

        this.repository.save(cliente);
    }
}
