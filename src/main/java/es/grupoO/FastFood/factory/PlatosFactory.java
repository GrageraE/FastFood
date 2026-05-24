package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.exceptions.NotValidEmailException;
import es.grupoO.FastFood.exceptions.RoleNotAllowedException;
import es.grupoO.FastFood.model.entity.Plato;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.state.CategoriaPlato;
import es.grupoO.FastFood.model.state.Divisa;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.model.valueobject.Precio;
import es.grupoO.FastFood.services.RestaurantesService;
import org.springframework.security.core.Authentication;

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
    
    public Plato fabricarPlato(Authentication auth) {
        Email email = Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("El mail no es valido"));

        Restaurante rest = restaurantesService.buscarRestaurantePorID(this.idRestaurante);
        CategoriaPlato cat = CategoriaPlato.fromInteger(categoriaPlato);
        Precio precio = new Precio(this.precio, Divisa.EURO);

        if(!rest.getEmail().equals(email)) {
            throw new RoleNotAllowedException("El restaurante autenticado no coincide con el ID dado");
        }
        
        return new Plato(this.nombre, rest, precio, cat);
    }
}
