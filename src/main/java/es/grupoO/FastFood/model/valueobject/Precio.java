package es.grupoO.FastFood.model.valueobject;

import es.grupoO.FastFood.exceptions.NotEqualCurrencyException;
import es.grupoO.FastFood.model.state.Divisa;

public class Precio {
    private double cantidad;
    
    private Divisa divisa;

    public Precio(double cantidad, Divisa divisa) {
        this.cantidad = cantidad;
        this.divisa = divisa;
    }

    public double getCantidad() {
        return cantidad;
    }

    public Divisa getDivisa() {
        return divisa;
    }
    /* Método para sumar dos precios, solo si tienen la misma divisa.
     * @param Precio
     * @return Precio
    */
    public Precio sumarPrecios(Precio p2) {
        if(this.divisa != p2.divisa) {
            throw new NotEqualCurrencyException("No se pueden sumar los precios");
        }
        return new Precio(this.cantidad + p2.cantidad, this.divisa);
    }

    /* Método para multiplicar un precio por un número, manteniendo la misma divisa.
     * @param int
     * @return Precio
    */
    public Precio multiplicarPrecios(int multiplo) {
        return new Precio(this.cantidad * multiplo, this.divisa);
    }
}
