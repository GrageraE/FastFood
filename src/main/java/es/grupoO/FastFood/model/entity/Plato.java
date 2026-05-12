package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.state.CategoriaPlato;
import es.grupoO.FastFood.model.valueobject.Precio;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Plato")
public class Plato {
    @Id
    private long idPlato;

    private String nombre;

    @DBRef
    private Restaurante restaurante;

    private Precio precio;

    private CategoriaPlato categoriaPlato;

    @DBRef
    private Rebaja rebaja;

    public long getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(long idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Precio getPrecio() {
        return precio;
    }

    public void setPrecio(Precio precio) {
        this.precio = precio;
    }

    public CategoriaPlato getCategoriaPlato() {
        return categoriaPlato;
    }

    public void setCategoriaPlato(CategoriaPlato categoriaPlato) {
        this.categoriaPlato = categoriaPlato;
    }

    public Rebaja getRebaja() {
        return rebaja;
    }

    public void setRebaja(Rebaja rebaja) {
        this.rebaja = rebaja;
    }

    public void quitarRebaja(){
        this.rebaja = null;
    }
}
