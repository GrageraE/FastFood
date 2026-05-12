package es.grupoO.FastFood.factory;

import es.grupoO.FastFood.model.entity.*;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.services.ClientesService;
import es.grupoO.FastFood.services.PlatosService;
import es.grupoO.FastFood.services.RestaurantesService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidosFactory {
    private ObjectId idCliente;
    private ObjectId idRestaurante;
    private List<Pair<ObjectId, Integer>> platos;
    private String horaPedido;
    
    @Autowired
    private ClientesService clientesService;
    @Autowired
    private RestaurantesService restaurantesService;
    @Autowired
    private PlatosService platosService;
    @Autowired

    public PedidosFactory(ObjectId idCliente, ObjectId idRestaurante, List<Pair<ObjectId, Integer>> platos, String horaPedido) {
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.platos = platos;
        this.horaPedido = horaPedido;
    }
    
    public Pedido fabricarPedido() {
        Cliente cliente = this.clientesService.buscarClientePorID(this.idCliente);
        Restaurante restaurante = this.restaurantesService.buscarRestaurantePorID(this.idRestaurante);
        LocalTime hora = LocalTime.parse(this.horaPedido);

        ArrayList<LineaPlatos> lineas = new ArrayList<>();
        
        for(var linea : this.platos) {
            ObjectId idPlato = linea.getFirst();
            int cantidad = linea.getSecond();

            Plato plato = this.platosService.buscarPlatoPorID(idPlato);
            lineas.add(new LineaPlatos(plato, cantidad));
        }
        
        return new Pedido(cliente, restaurante, lineas);
    }
}
