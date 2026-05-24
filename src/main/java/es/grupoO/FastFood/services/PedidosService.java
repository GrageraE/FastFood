package es.grupoO.FastFood.services;

import es.grupoO.FastFood.exceptions.AlreadyAssignedException;
import es.grupoO.FastFood.model.entity.LineaPlatos;
import es.grupoO.FastFood.model.entity.Pedido;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.state.EstadoPedido;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.model.valueobject.Posicion;
import es.grupoO.FastFood.model.valueobject.Precio;
import es.grupoO.FastFood.repository.LineaPlatosRepository;
import es.grupoO.FastFood.repository.PedidosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import es.grupoO.FastFood.factory.PedidosFactory;

import java.util.List;
import es.grupoO.FastFood.exceptions.NoExistDBException;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private RepartidoresService repartidoresService;
    @Autowired
    private LineaPlatosRepository lineaPlatosRepository;
    @Autowired
    private ClientesService clientesService;
    @Autowired
    private RestaurantesService restaurantesService;
    @Autowired
    private PlatosService platosService;

    /**
     * Realiza un pedido a partir de la informacion dada, guardando el pedido y sus lineas en la base de datos
     * @param idCliente El ID del cliente solicitante
     * @param idRest El ID del restaurante al que se le hace el pedido
     * @param platos Una lista de ID de platos junto con sus cantidades deseadas
     * @return pedido
     */
    public Pedido realizarPedido(String idCliente, String idRest, List<Pair<String, Integer>> platos) {
        PedidosFactory  pedidosFactory = new PedidosFactory(
                idCliente, idRest, platos, this.clientesService, this.restaurantesService, this.platosService
        );

        Pedido pedido = pedidosFactory.fabricarPedido();

        this.lineaPlatosRepository.saveAll(pedido.getPlatos());
        this.pedidosRepository.save(pedido);

        return pedido;
    }

    /**
     * Busca el pedido por ID
     * @param idPedido El ID del pedido
     * @throws NoExistDBException si el pedido no esta registrado
     * @return pedido
     */
    public Pedido buscarPedidoPorID(String idPedido) {
        Pedido pedido = this.pedidosRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }
        return pedido;
    }

    public Precio obtenerPrecioPedido(String idPedido) {
        return this.buscarPedidoPorID(idPedido).precioTotal();
    }

    /**
     * Anula el pedido asociado a un ID
     * @param idPedido El ID de pedido
     * @throws NoExistDBException si el pedido no esta registrado
     */
    public void anularPedido(String idPedido) {
        Pedido pedido = this.pedidosRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }

        this.lineaPlatosRepository.deleteAll(pedido.getPlatos());
        this.pedidosRepository.deleteById(idPedido);
    }

    /**
     * Cambia el estado del pedido al estado dado por el usuario
     * @param idPedido El ID del pedido a modificar
     * @param estado El nuevo estado del pedido
     * @throws NoExistDBException si el pedido no esta registrado
     */
    public void cambiarEstado(String idPedido, int estado) {
        Pedido pedido = this.pedidosRepository.findById(idPedido).orElse(null);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }
        EstadoPedido estadoPedido = EstadoPedido.fromInteger(estado);
        if(EstadoPedido.ENTREGADO.equals(estadoPedido)) {
            this.lineaPlatosRepository.deleteAll(pedido.getPlatos());
            this.pedidosRepository.deleteById(idPedido);
        } else {
            pedido.setEstado(estadoPedido);
            this.pedidosRepository.save(pedido);
        }
    }

    /**
     * Asigna el pedido al repartidor con ese ID. Cambia el estado a EN_REPARTO
     * @param idPedido El ID del pedido a modificar
     * @param idRepartidor El ID del repartidor que se asigna
     * @throws NoExistDBException si el pedido o el repartidor no estan registrados
     * @throws AlreadyAssignedException si el pedido ya tiene un repartidor asignado
     */
    public void asignarPedido(String idPedido, String idRepartidor) {
        Pedido pedido = this.buscarPedidoPorID(idPedido);
        if(pedido == null) {
            throw new NoExistDBException("El pedido no esta registrado");
        }
        Repartidor rep = this.repartidoresService.buscarRepartidorPorID(idRepartidor);

        if(!pedido.repartidorAsignado()) {
            pedido.asignarRepartidor(rep);
            pedido.setEstado(EstadoPedido.EN_REPARTO);
            this.pedidosRepository.save(pedido);
        } else {
            throw new AlreadyAssignedException("Repartidor ya asignado");
        }
    }

    /**
     * Devuelve una lista de los restaurantes con pedidos disponibles mas cercanos dada la ubicacion del repartidor
     * @param posicionRepartidor La ubicacion del repartidor
     * @param pageable Paginacion
     * @return pageable<Pedido>
     */
    public Page<Pedido> buscarPedidosARepartir(Posicion posicionRepartidor, Pageable pageable) {
        return this.pedidosRepository.buscarPorUbicacion(posicionRepartidor, pageable);
    }
}
