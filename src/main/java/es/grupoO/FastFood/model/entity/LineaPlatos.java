package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.valueobject.Precio;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("LineaPlatos")
public class LineaPlatos {
    @Id
    private long idLineaPlatos;

    @DBRef
    private Plato plato;

    private int cantidad;

    public LineaPlatos(Plato plato, int cantidad) {
        this.plato = plato;
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Precio precioSubtotal() {
        // TODO: Hacer precio
        return null;
    }
}
