package es.grupoO.FastFood.model.entity;

import java.time.LocalDate;

import es.grupoO.FastFood.model.valueobject.Precio;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Rebaja")
public class Rebaja {
    @Id
    private ObjectId idRebaja;

    private Precio nuevoPrecio;

    private LocalDate fechaLimite;

    public Rebaja(Precio nuevoPrecio, LocalDate fechaLimite) {
        this.nuevoPrecio = nuevoPrecio;
        this.fechaLimite = fechaLimite;
    }

    public ObjectId getIdRebaja() {
        return idRebaja;
    }

    public void setIdRebaja(ObjectId idRebaja) {
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
