package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.valueobject.Email;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Repartidor")
public class Repartidor {
    @Id
    private ObjectId idRepartidor;
    
    private String nombre;
    
    private String telefonoContacto;

    private Email emailRepartidor;

    private String hashPassword;
    
    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
    
    public Repartidor(String nombre, String telefonoContacto, Email emailRepartidor, String hashPassword) {
        this.nombre = nombre;
        this.telefonoContacto = telefonoContacto;
        this.emailRepartidor = emailRepartidor;
        this.hashPassword = hashPassword;
    }

    public ObjectId getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(ObjectId idRepartidor) {
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
        return this.emailRepartidor;
    }

    public void setEmail(Email emailRepartidor) {
        this.emailRepartidor = emailRepartidor;
    }
}
