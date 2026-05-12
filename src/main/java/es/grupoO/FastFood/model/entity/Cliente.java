package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.valueobject.Email;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Cliente")
public class Cliente {
    @Id
    private ObjectId idCliente;

    private String nombre;

    private String direccionEnvio;

    private String telefonoContacto;

    private Email emailCliente;

    private String hashPassword;

    public Cliente(String nombre, String direccionEnvio, String telefonoContacto, Email emailCliente, String hashPassword) {
        this.nombre = nombre;
        this.direccionEnvio = direccionEnvio;
        this.telefonoContacto = telefonoContacto;
        this.emailCliente = emailCliente;
        this.hashPassword = hashPassword;
    }

    public ObjectId getIdCliente() {
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

    public String gethashPassword(){
        return this.hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }
}
