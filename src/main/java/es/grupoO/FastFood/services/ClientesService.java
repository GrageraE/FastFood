package es.grupoO.FastFood.services;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.repository.ClientesRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {
    ClientesRepository repositoy;
    
    public Cliente validar(String email, String passwd) {
        return null;
    }
    
    public void insertarCliente(String nombre, String direccion, String telefono, String email, String passwd) {
        // TODO
    }
    
    public Cliente buscarClientePorID(long idCliente) {
        // TODO: Que hacer si el ID no existe?
        return this.repositoy.findById(idCliente).get();
    }
}
