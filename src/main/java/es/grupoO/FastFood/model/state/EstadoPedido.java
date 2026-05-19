package es.grupoO.FastFood.model.state;

import es.grupoO.FastFood.exceptions.InvalidCategoryException;

public enum EstadoPedido {
    EN_PREPARACION,
    LISTO_PARA_ENTREGAR,
    EN_REPARTO,
    ENTREGADO;

    public static EstadoPedido fromInteger(int estado) {
        if(estado < 0 || estado >= EstadoPedido.values().length) {
            throw new InvalidCategoryException("El numero de estado de pedido dado es invalido");
        }

        return EstadoPedido.values()[estado];
    }
}
