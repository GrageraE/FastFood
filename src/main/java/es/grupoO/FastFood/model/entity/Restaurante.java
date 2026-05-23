package es.grupoO.FastFood.model.entity;

import java.time.LocalTime;

import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.model.valueobject.Posicion;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Restaurantes")
public class Restaurante {
    @Id
    private String idRestaurante;

    private String nombre;

    private String direccion;

    private String telefono;

    private LocalTime horaApertura;

    private LocalTime horaCierre;

    private CategoriaRestaurante categoria;

    private String hashPassword;
    
    @DBRef
    private Valoracion valoracion;

    private Email email;

    private Posicion posicion;

    public Restaurante(String nombre, String direccion, String telefono, LocalTime horaApertura, 
                       LocalTime horaCierre, CategoriaRestaurante categoria, Valoracion valoracion, Email email, 
                       String hashPassword, Posicion posicion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.categoria = categoria;
        this.valoracion = valoracion;
        this.email = email;
        this.hashPassword = hashPassword;
        this.posicion = posicion;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalTime getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(LocalTime horaApertura) {
        this.horaApertura = horaApertura;
    }

    public LocalTime getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(LocalTime horaCierre) {
        this.horaCierre = horaCierre;
    }

    public CategoriaRestaurante getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRestaurante categoria) {
        this.categoria = categoria;
    }

    public Valoracion getValoracion() {
        return valoracion;
    }

    public void agregarValoracion(int puntuacion) {
        this.valoracion.actualizarValoracion(puntuacion);
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Boolean estaAbierto(LocalTime hora) {
        return hora.isAfter(this.horaApertura) && hora.isBefore(this.horaCierre);
    }

    public String gethashPassword(){
        return this.hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

}
