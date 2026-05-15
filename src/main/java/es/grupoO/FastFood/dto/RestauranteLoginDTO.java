package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Email;

public class RestauranteLoginDTO {
    private String idRestaurante;

    private Email email;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }
}
