package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.state.CategoriaPlato;
import es.grupoO.FastFood.model.valueobject.Precio;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document("Plato")
public class Plato {
    @Id
    private ObjectId idPlato;

    private String nombre;

    @DBRef
    private Restaurante restaurante;

    private Precio precio;

    private CategoriaPlato categoriaPlato;
    
    @DBRef
    private Rebaja rebaja;

    public Plato(String nombre, Restaurante restaurante, Precio precio, CategoriaPlato categoriaPlato) {
        this.nombre = nombre;
        this.restaurante = restaurante;
        this.precio = precio;
        this.categoriaPlato = categoriaPlato;
        this.rebaja = null;
    }

    public ObjectId getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(ObjectId idPlato) {
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
