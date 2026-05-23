package es.grupoO.FastFood.dto;

/**
 * DTO para recoger los datos de un Repartidor cuando se va a crear una cuenta. Necesario para el @RequestBody
 */
public class RepartidorInsertDTO {
    private String nombre;
    private String telefono;
    private String email;
    private String passw;

    public RepartidorInsertDTO(String nombre, String telefono, String email, String passw) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.passw = passw;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
}
