package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.model.entity.*;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.services.ClientesService;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.services.RestaurantesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PedidosFactory {
    private String idCliente;
    private String idRestaurante;
    private List<Pair<String, Integer>> platos;
    
    @Autowired
    private ClientesService clientesService;
    @Autowired
    private RestaurantesService restaurantesService;
    @Autowired
    private PlatosService platosService;

    public PedidosFactory(String idCliente, String idRestaurante, List<Pair<String, Integer>> platos) {
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.platos = platos;
    }
    
    public Pedido fabricarPedido() {
        Cliente cliente = this.clientesService.buscarClientePorID(this.idCliente);
        Restaurante restaurante = this.restaurantesService.buscarRestaurantePorID(this.idRestaurante);

        ArrayList<LineaPlatos> lineas = new ArrayList<>();
        
        for(Pair<String, Integer> linea : this.platos) {
            String idPlato = linea.getFirst();
            int cantidad = linea.getSecond();

            Plato plato = this.platosService.buscarPlatoPorID(idPlato);
            lineas.add(new LineaPlatos(plato, cantidad));
        }
        
        return new Pedido(cliente, restaurante, lineas);
    }
}
