package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Email;

public class RepartidorLoginDTO {
    private String idRepartidor;

    private Email emailRepartidor;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public Email getEmailRepartidor() {
        return emailRepartidor;
    }

    public String getToken() {
        return token;
    }
}
