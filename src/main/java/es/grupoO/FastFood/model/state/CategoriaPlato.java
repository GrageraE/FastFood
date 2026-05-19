package es.grupoO.FastFood.model.state;

import es.grupoO.FastFood.exceptions.InvalidCategoryException;

public enum CategoriaPlato {
    HAMBURGUESA,
    BEBIDA,
    POSTRE,
    PIZZA,
    ENTRANTE;

    public static CategoriaPlato fromInteger(int categoria) {
        if(categoria < 0 || categoria >= CategoriaPlato.values().length) {
            throw new InvalidCategoryException("Numero de categoria de plato invalido");
        }

        return CategoriaPlato.values()[categoria];
    }
}
