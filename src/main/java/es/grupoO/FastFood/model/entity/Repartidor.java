package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Repartidor")
public class Repartidor {
    @Id
    private int idRepartidor;
    
    private String nombre;
    
    private String telefonoContacto;

    private Email emailRepartidor;
    
    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
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
