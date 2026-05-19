package es.grupoO.FastFood.model.entity;

import es.grupoO.FastFood.exceptions.InvalidRatingException;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Valoracion")
public class Valoracion {
    @Id
    private String idValoracion;
    
    private int nValoraciones;
    
    private double valor;
    
    public Valoracion() {
        this.valor = 0.0;
        this.nValoraciones = 0;
    }
    
    public Valoracion(double valor, int nValoraciones) {
        this.valor = valor;
        this.nValoraciones = nValoraciones;
    }

    public double getValor() {
        return valor;
    }
    
    public int getNValoraciones() {
        return nValoraciones;
    }

    /**
     * Actualiza la valoracion
     * @param nuevoValor El nuevo valor
     */
    public void actualizarValoracion(int nuevoValor) {
        if(nuevoValor < 1 || nuevoValor > 5) {
            throw new InvalidRatingException("La valoracion enviada es invalida");
        }
        double sumaVal = this.nValoraciones * this.valor;
        sumaVal += nuevoValor;
        this.nValoraciones++;
        this.valor = sumaVal / this.nValoraciones;
    }
}
