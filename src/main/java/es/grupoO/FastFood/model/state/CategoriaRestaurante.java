package es.grupoO.FastFood.model.state;

import es.grupoO.FastFood.exceptions.InvalidCategoryException;

public enum CategoriaRestaurante {
    EXICANO,
    ITALIANO,
    CHINO,
    AMERICANO;

    public static CategoriaRestaurante fromInteger(int categoria) {
        if(categoria < 0 || categoria >= CategoriaRestaurante.values().length) {
            throw new InvalidCategoryException("Numero de categoria de restaurante invalido");
        }

        return CategoriaRestaurante.values()[categoria];
    }
}
