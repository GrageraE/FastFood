package es.grupoO.FastFood.services;

import es.grupoO.FastFood.dto.ClienteLoginDTO;
import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.factory.ClienteFactory;
import es.grupoO.FastFood.auth.HashMaker;
import es.grupoO.FastFood.mapper.ClienteLoginMapper;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.security.core.Authentication;
import es.grupoO.FastFood.exceptions.NotValidEmailException;
import es.grupoO.FastFood.exceptions.UsernameAlreadyExistException;

@Service
public class ClientesService {
    @Autowired
    ClientesRepository repository;

    @Autowired
    AuthService authService;

    @Autowired
    ClienteLoginMapper clienteMapper;
    
    public Cliente insertarCliente(String nombre, String direccion, String telefono, String email, String passwd) {
        Email parsedEmail = Email.parse(email)
                .orElseThrow(() -> new NotValidEmailException("El email proporcionado no es correcto"));

        if(this.repository.findByEmail(parsedEmail) != null) {
            throw new UsernameAlreadyExistException("El cliente ya existe");
        }

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
        return this.repository.findById(idCliente).orElseThrow(() -> new NoExistDBException("ID no encontrado"));
    }
    
    public void changePasswdCliente(String newPasswd, Authentication auth) {
        HashMaker hasher = new HashMaker();
        Email email = Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("Email proporcionado no valido"));
        //no hay que comprobar el email, porque el token ya ha sido validado.
        Cliente cliente = this.repository.findByEmail(email);
        cliente.setHashPassword(hasher.encoder(newPasswd));
        this.repository.save(cliente);
    }

    public void eliminarCliente(Authentication auth) {
        Cliente cliente = this.repository.findByEmail(Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("Email proporcionado no valido")));
        this.repository.delete(cliente);
    }
}
