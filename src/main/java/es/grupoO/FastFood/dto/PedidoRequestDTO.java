package es.grupoO.FastFood.dto;

import es.grupoO.FastFood.model.valueobject.Pair;

import java.util.List;

/**
 * DTO para almacenar los datos de solicitud de pedido.
 */
public class PedidoRequestDTO {
    private String idCliente;
    private String idRest;
    private List<Pair<String, Integer>> platos;

    public PedidoRequestDTO(String idCliente, String idRest, List<Pair<String, Integer>> platos) {
        this.idCliente = idCliente;
        this.idRest = idRest;
        this.platos = platos;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdRest() {
        return idRest;
    }

    public void setIdRest(String idRest) {
        this.idRest = idRest;
    }

    public List<Pair<String, Integer>> getPlatos() {
        return platos;
    }

    public void setPlatos(List<Pair<String, Integer>> platos) {
        this.platos = platos;
    }
}
