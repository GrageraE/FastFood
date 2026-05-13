package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.auth.HashMaker;

public class ClienteFactory {
    private String nombre;
    private String direccion;
    private String telefono;
    private String emailString;
    private String passwd;

    private HashMaker hasher;

    public ClienteFactory(String nombre, String direccion, String telefono, String email, String passwd) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.emailString = email;
        this.passwd = passwd;
        this.hasher = new HashMaker();
    }

    public Cliente crearCliente() {
        Email email = Email.parse(this.emailString);
        String hashPasswd = hasher.encoder(passwd);
        return new Cliente(nombre, direccion, telefono, email, hashPasswd);
    }
}
