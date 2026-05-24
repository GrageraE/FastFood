package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.exceptions.InvalidTimeException;
import es.grupoO.FastFood.exceptions.NotValidEmailException;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Valoracion;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.auth.HashMaker;
import es.grupoO.FastFood.model.valueobject.Posicion;
import es.grupoO.FastFood.services.GeocodingService;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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

    private GeocodingService geocodingService;

    public RestauranteFactory(String nombre, String direccion,
                              String telefono, String horaApertura,
                              String horaCierre, int categoria, String email, String passwd,
                              GeocodingService geocodingService) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.categoria = categoria;
        this.email = email;
        this.hasher = new HashMaker();
        this.passwd = passwd;
        this.geocodingService = geocodingService;
    }

    public Restaurante fabricarRestaurante() {
        LocalTime horaAp;
        LocalTime horaC;
        try {
            horaAp = LocalTime.parse(this.horaApertura);
            horaC = LocalTime.parse(this.horaCierre);
        } catch (DateTimeParseException e) {
            throw new InvalidTimeException("Tiempo invalido: " + e.getParsedString() + " -- Razon: " + e.getMessage());
        }

        Email emailParsed = Email.parse(this.email)
                .orElseThrow(() -> new NotValidEmailException("El email del restaurante no es valido"));
        String hashPasswd = hasher.encoder(passwd);

        CategoriaRestaurante cat = CategoriaRestaurante.fromInteger(categoria);

        Valoracion val = new Valoracion();

        Posicion posicion = geocodingService.obtenerCoordenadas(this.direccion);

        return new Restaurante(this.nombre, this.direccion, this.telefono,
                horaAp, horaC, cat, val, emailParsed, hashPasswd, posicion.toGeoJson());
    }


}
