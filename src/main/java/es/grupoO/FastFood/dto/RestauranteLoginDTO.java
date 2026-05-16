package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Email;

public class RestauranteLoginDTO {
    private String idRestaurante;

    private Email email;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdRestaurante() {
        return idRestaurante;
    }

    public Email getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setIdRestaurante(String idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
