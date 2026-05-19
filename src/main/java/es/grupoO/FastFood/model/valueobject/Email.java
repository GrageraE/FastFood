package es.grupoO.FastFood.model.valueobject;

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
    
    public static Email parse(String dir) {
        String[] splitted = dir.split("@");
        String userName = splitted[0];
        String domain  = splitted[1];
        return new Email(userName, domain);
    }
    
    public static boolean validarEmail(String email) {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false;
        }
        return !parts[0].isEmpty() && !parts[1].isEmpty();
    }

    @Override
    public String toString() {
        return userName + "@" + servidor;
    }
}
