package es.grupoO.FastFood.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public final class HashMaker {
    private BCryptPasswordEncoder encoder;

    public HashMaker() {
        this.encoder = new BCryptPasswordEncoder();
    }

    public String encoder(String password){
        return this.encoder.encode(password);
    }

    public Boolean verify(String passwordBaseDatos, String password){
        return this.encoder.matches(password, passwordBaseDatos);
    }
}
