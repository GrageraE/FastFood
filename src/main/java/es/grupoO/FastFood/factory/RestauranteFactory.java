package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Valoracion;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.hasher.HashMaker;

import java.time.LocalTime;

public class RestauranteFactory {
    private String nombre;
    private String direccion;
    private String telefono;
    private String horaApertura;
    private String horaCierre;
    private int categoria;
    private String email;
    private String passwd;

    private HashMaker hasher;

    public RestauranteFactory(String nombre, String direccion,
                              String telefono, String horaApertura,
                              String horaCierre, int categoria, String email, String passwd) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.categoria = categoria;
        this.email = email;
        this.hasher = new HashMaker();
        this.passwd = passwd;
    }

    public Restaurante fabricarRestaurante() {
        LocalTime horaAp = LocalTime.parse(this.horaApertura);
        LocalTime horaC = LocalTime.parse(this.horaCierre);
        Email emailParsed = Email.parse(this.email);

        String hashPasswd = hasher.encoder(passwd);

        CategoriaRestaurante cat = CategoriaRestaurante.values()[this.categoria];

        Valoracion val = new Valoracion();

        return new Restaurante(this.nombre, this.direccion, this.telefono,
                horaAp, horaC, cat, val, emailParsed, hashPasswd);
    }
}
