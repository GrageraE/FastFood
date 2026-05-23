package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.exceptions.EmptyOrderException;
import es.grupoO.FastFood.model.state.EstadoPedido;
import es.grupoO.FastFood.model.valueobject.Precio;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Pedidos")
public class Pedido {
    @Id
    private String idPedido;

    @DBRef
    private Cliente cliente;

    @DBRef
    private Restaurante restaurante;

    @DBRef
    private Repartidor repartidor;

    private EstadoPedido estadoPedido;

    @DBRef
    private List<LineaPlatos> platos;

    private LocalTime horaPedido;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint posicion;

    public Pedido(Cliente cliente, Restaurante restaurante, List<LineaPlatos> platos, GeoJsonPoint posicion) {
        this.restaurante = restaurante;
        this.cliente = cliente;

        this.repartidor = null;
        this.estadoPedido = EstadoPedido.EN_PREPARACION;
        this.horaPedido = LocalTime.now();
        this.platos = platos;
        this.posicion = posicion;
    }

    public String getIdPedido() {
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

    public List<LineaPlatos> getPlatos() {
        return this.platos;
    }

    /*
     * Método para calcular el precio total del pedido.
     * @return Precio
     */
    public Precio precioTotal() {
        return this.platos.stream()
                .map(x -> x.precioSubtotal())
                .reduce((a, b) -> a.sumarPrecios(b))
                .orElseThrow(() -> new EmptyOrderException("Pedido vacio"));
    }

    public LocalTime fechaRealizacion(){
        return this.horaPedido;
    }

    public EstadoPedido getEstadoPedido(){
        return this.estadoPedido;
    }

    public GeoJsonPoint getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoJsonPoint posicion) {
        this.posicion = posicion;
    }
}
