package es.grupoO.FastFood.model.entity;

import java.time.LocalDate;

import es.grupoO.FastFood.model.valueobject.Precio;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Rebaja")
public class Rebaja {
    @Id
    private String idRebaja;

    private Precio nuevoPrecio;

    private LocalDate fechaLimite;

    public Rebaja(Precio nuevoPrecio, LocalDate fechaLimite) {
        this.nuevoPrecio = nuevoPrecio;
        this.fechaLimite = fechaLimite;
    }

    public String getIdRebaja() {
        return idRebaja;
    }

    public void setIdRebaja(String idRebaja) {
        this.idRebaja = idRebaja;
    }

    public Precio nuevoPrecio() {
        return nuevoPrecio;
    }

    public void setNuevoPrecio(Precio nuevoPrecio) {
        this.nuevoPrecio = nuevoPrecio;
    }

    public LocalDate fechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
