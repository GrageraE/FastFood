package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Email;

public class ClienteLoginDTO {
    private String idCliente;

    private Email email;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public Email getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
