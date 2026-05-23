package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Email;

/**
 * DTO para entregar los datos de inicio de sesion a un Repartidor. Contiene su ID, su correo y su token
 */
public class RepartidorLoginDTO {
    private String idRepartidor;

    private Email email;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdRepartidor() {
        return idRepartidor;
    }

    public Email getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}
