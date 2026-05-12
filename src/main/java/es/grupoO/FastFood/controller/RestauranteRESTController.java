package es.grupoO.FastFood.controller;

import org.springframework.web.bind.annotation.RestController;
import es.grupoO.FastFood.services.RestaurantesService;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Plato;
import java.util.ArrayList;
import java.util.List;


@RestController
public class RestauranteRESTController {
    private final RestaurantesService restaurantesService;

    private final PlatosService platosService;

    public RestauranteRESTController(RestaurantesService restaurantesService, PlatosService platosService) {
        this.restaurantesService = restaurantesService;
        this.platosService = platosService;
    }

    public Restaurante validarRestaurante(String email, String password) {
        return restaurantesService.validar(email, password);
    }

      public Restaurante buscarRestaurantePorID(long idRest) {
        return this.restaurantesService.buscarRestaurantePorID(idRest);
    }

}
