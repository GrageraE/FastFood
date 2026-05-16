package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Email;

public class ClienteLoginDTO {
    private String idCliente;

    private Email emailCliente;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public Email getEmailCliente() {
        return emailCliente;
    }

    public String getToken() {
        return token;
    }
}
