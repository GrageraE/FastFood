package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.valueobject.Email;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Cliente")
public class Cliente {
    @Id
    private long idCliente;

    private String nombre;

    private String direccionEnvio;

    private String telefonoContacto;

    private Email emailCliente;

    public Cliente(int idCliente, String nombre, String direccionEnvio, String telefonoContacto, Email emailCliente) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccionEnvio = direccionEnvio;
        this.telefonoContacto = telefonoContacto;
        this.emailCliente = emailCliente;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public Email getEmail(){
        return this.emailCliente;
    }

    public void setEmail(Email emailCliente) {
        this.emailCliente = emailCliente;
    }
}
