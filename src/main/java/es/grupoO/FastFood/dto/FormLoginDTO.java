package es.grupoO.FastFood.dto;

/**
 * DTO para recoger los formularios de inicio de sesion. Este DTO sirve para los tres roles,
 * ya que los formularios son identicos
 */
public class FormLoginDTO {
    private String email;
    private String passwd;

    public FormLoginDTO(String email, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    // Getters y Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswd() { return passwd; }
    public void setPasswd(String passwd) { this.passwd = passwd; }
}

