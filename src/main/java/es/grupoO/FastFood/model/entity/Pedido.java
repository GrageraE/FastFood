package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.model.state.EstadoPedido;
import es.grupoO.FastFood.model.valueobject.Precio;

import java.util.ArrayList;
import java.time.LocalTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Pedidos")
public class Pedido {
    @Id
    private ObjectId idPedido;

    @DBRef
    private Cliente cliente;

    @DBRef
    private Restaurante restaurante;

    @DBRef
    private Repartidor repartidor;

    private EstadoPedido estadoPedido;

    @DBRef
    private ArrayList<LineaPlatos> platos;

    private LocalTime horaPedido;

    public Pedido(Cliente cliente, Restaurante restaurante, ArrayList<LineaPlatos> lineasPlatos) {
        this.restaurante = restaurante;
        this.cliente = cliente;

        this.repartidor = null;
        this.estadoPedido = EstadoPedido.EN_PREPARACION;
        this.repartidor = null;
        this.horaPedido = LocalTime.now();
        this.platos = lineasPlatos;
    }

    public ObjectId getIdPedido() {
        return idPedido;
    }

    public EstadoPedido estado() {
        return this.estadoPedido;
    }

    public void avanzarEstado() {
        switch (this.estadoPedido) {
            case EN_PREPARACION -> this.estadoPedido = EstadoPedido.LISTO_PARA_ENTREGAR;
            case LISTO_PARA_ENTREGAR -> this.estadoPedido = EstadoPedido.EN_REPARTO;
            case EN_REPARTO -> this.estadoPedido = EstadoPedido.ENTREGADO;
        }
    }
    
    public void setEstado(EstadoPedido estado) {
        this.estadoPedido = estado;
    }

    public void asignarRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public Repartidor getRepartidor() {
        return this.repartidor;
    }

    public boolean repartidorAsignado() {
        return this.repartidor != null;
    }

    public Restaurante getRestaurante() {
        return this.restaurante;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public ArrayList<LineaPlatos> getPlatos() {
        return this.platos;
    }

    public Precio precioTotal() {
        // TODO: Comprobar si tiene valor
        if(this.platos.isEmpty()) {
            // TODO: Lanzar excepcion
        }

        return this.platos.stream()
                .map(x -> x.precioSubtotal())
                .reduce((a, b) -> a.sumarPrecios(b))
                .get();
    }

    public LocalTime fechaRealizacion(){
        return this.horaPedido;
    }

    public EstadoPedido getEstadoPedido(){
        return this.estadoPedido;
    }
}
