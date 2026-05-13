package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.auth.HashMaker;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.valueobject.Email;

public class RepartidorFactory {
    private String nombre;
    private String telefono;
    private String email;
    private String passwd;
    
    private HashMaker hasher;
    
    public RepartidorFactory(String nombre, String telefono, String email, String passwd) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.passwd = passwd;
    }
    
    public Repartidor fabricarRepartidor() {
        Email emailParsed = Email.parse(this.email);
        String hashPassword = this.hasher.encoder(this.passwd);
        return new Repartidor(nombre, telefono, emailParsed, hashPassword);
    }
}
