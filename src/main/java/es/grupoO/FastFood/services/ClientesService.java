package es.grupoO.FastFood.services;

import es.grupoO.FastFood.dto.ClienteLoginDTO;
import es.grupoO.FastFood.factory.ClienteFactory;
import es.grupoO.FastFood.auth.HashMaker;
import es.grupoO.FastFood.mapper.ClienteLoginMapper;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.exceptions.RoleNotAllowedException;
import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.security.core.Authentication;

@Service
public class ClientesService {
    @Autowired
    ClientesRepository repository;

    @Autowired
    AuthService authService;

    @Autowired
    ClienteLoginMapper clienteMapper;
    
    public Cliente insertarCliente(String nombre, String direccion, String telefono, String email, String passwd) {
        ClienteFactory clienteFactory = new ClienteFactory(nombre, direccion, telefono, email, passwd);
        Cliente cliente = clienteFactory.crearCliente();

        this.repository.save(cliente);
        return cliente;
    }

    public ClienteLoginDTO validar(String email, String passwd) {
        Pair<Cliente, String> data = this.authService.loginCliente(email, passwd);
        return this.clienteMapper.fromPair(data);
    }
    
    public Cliente buscarClientePorID(String idCliente) {
        return this.repository.findById(idCliente).get();
    }
    
    public void changePasswdCliente(String newPasswd, Authentication auth) {
        HashMaker hasher = new HashMaker();
        Email email = Email.parse(auth.getName());
        //no hay que comprobar el email, porque el token ya ha sido validado.
        Cliente cliente = this.repository.findByEmail(email);
        cliente.setHashPassword(hasher.encoder(newPasswd));
        this.repository.save(cliente);
    }
}
