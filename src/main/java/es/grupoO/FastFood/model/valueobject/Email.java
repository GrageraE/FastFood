package es.grupoO.FastFood.model.valueobject;

import java.util.Optional;

public class Email {
    private String userName;
    
    private String servidor;

    public Email(String userName, String servidor) {
        this.userName = userName;
        this.servidor = servidor;
    }

    public String getUserName() {
        return userName;
    }
    public String getServidor() {
        return servidor;
    }
    
    /*
     * Método para parsear un string en formato "userName@servidor" a un objeto Email.
     * @param String
     * @return Optional<Email>
     */
    public static Optional<Email> parse(String dir) {
        String[] parts = dir.split("@");
        if (parts.length != 2 && !parts[0].isBlank() && !parts[1].isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new Email(parts[0], parts[1]));
    }

    @Override
    public String toString() {
        return userName + "@" + servidor;
    }
}
