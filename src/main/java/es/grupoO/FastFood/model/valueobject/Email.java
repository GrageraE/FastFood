package es.grupoO.FastFood.model.valueobject;

public class Email {
    private String userName;
    
    private String servidor;

    public Email(String userName, String servidor) {
        this.userName = userName;
        this.servidor = servidor;
    }

    public String getEmail() {
        return userName + "@" + servidor;
    }

    public String getUserName() {
        return userName;
    }
    public String getServidor() {
        return servidor;
    }
}
