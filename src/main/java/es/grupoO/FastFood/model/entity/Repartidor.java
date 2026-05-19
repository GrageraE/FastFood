package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Repartidor")
public class Repartidor {
    @Id
    private String idRepartidor;
    
    private String nombre;
    
    private String telefonoContacto;

    private Email email;

    private String hashPassword;
    
    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
    
    public Repartidor(String nombre, String telefonoContacto, Email email, String hashPassword) {
        this.nombre = nombre;
        this.telefonoContacto = telefonoContacto;
        this.email = email;
        this.hashPassword = hashPassword;
    }

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public Email getEmail() {
        return this.email;
    }

    public void setEmail(Email emailRepartidor) {
        this.email = emailRepartidor;
    }

}
