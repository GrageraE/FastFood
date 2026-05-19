package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.CategoriaPlato;
import es.grupoO.FastFood.model.state.Divisa;
import es.grupoO.FastFood.model.valueobject.Precio;
import es.grupoO.FastFood.services.RestaurantesService;

public class PlatosFactory {
    private String nombre;
    private String idRestaurante;
    private double precio;
    private int categoriaPlato;
    
    private RestaurantesService restaurantesService;

    public PlatosFactory(String nombre, String idRestaurante, double precio, int categoriaPlato,
                         RestaurantesService restaurantesService) {
        this.nombre = nombre; 
        this.idRestaurante = idRestaurante;
        this.precio = precio;
        this.categoriaPlato = categoriaPlato;
        this.restaurantesService = restaurantesService;
    }
    
    public Plato fabricarPlato() {
        Restaurante rest = restaurantesService.buscarRestaurantePorID(this.idRestaurante);
        CategoriaPlato cat = CategoriaPlato.fromInteger(categoriaPlato);
        Precio precio = new Precio(this.precio, Divisa.EURO);
        
        return new Plato(this.nombre, rest, precio, cat);
    }
}
