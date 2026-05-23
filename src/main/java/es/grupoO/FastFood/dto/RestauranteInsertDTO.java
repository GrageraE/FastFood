package es.grupoO.FastFood.dto;

/**
 * DTO para recoger los datos de un Restaurante cuando se va a crear una cuenta. Necesario para el @RequestBody
 */
public class RestauranteInsertDTO {
    private String nombre;
    private int categoria;
    private String direccion;
    private String telefono;
    private String email;
    private String horaApertura;
    private String horaCierre;
    private String passwd;

    public RestauranteInsertDTO(String nombre, int categoria, String direccion, String telefono, String email, String horaApertura, String horaCierre, String passwd) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.passwd = passwd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
