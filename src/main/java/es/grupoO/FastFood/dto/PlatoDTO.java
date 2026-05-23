package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.state.CategoriaPlato;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Precio;

public class PlatoDTO {
    private String idPlato;

    private String nombre;

    private String nombreRestaurante;

    private CategoriaRestaurante categoriaRestaurante;

    private Precio precio;

    private CategoriaPlato categoriaPlato;

    public PlatoDTO(String idPlato, String nombre, String nombreRestaurante, CategoriaRestaurante categoriaRestaurante,
                    Precio precio, CategoriaPlato categoriaPlato) {
        this.idPlato = idPlato;
        this.nombre = nombre;
        this.nombreRestaurante = nombreRestaurante;
        this.categoriaRestaurante = categoriaRestaurante;
        this.precio = precio;
        this.categoriaPlato = categoriaPlato;
    }

    public String getIdPlato() {
        return idPlato;
    }

    public void setIdPlato(String idPlato) {
        this.idPlato = idPlato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreRestaurante() {
        return nombreRestaurante;
    }

    public void setNombreRestaurante(String nombreRestaurante) {
        this.nombreRestaurante = nombreRestaurante;
    }

    public CategoriaRestaurante getCategoriaRestaurante() {
        return categoriaRestaurante;
    }

    public void setCategoriaRestaurante(CategoriaRestaurante categoriaRestaurante) {
        this.categoriaRestaurante = categoriaRestaurante;
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
}
