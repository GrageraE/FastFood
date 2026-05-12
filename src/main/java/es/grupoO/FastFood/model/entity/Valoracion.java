package es.grupoO.FastFood.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Valoracion")
public class Valoracion {
    @Id
    private ObjectId idValoracion;
    
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
            // TODO: Lanzar excepcion
        }
        double sumaVal = this.nValoraciones * this.valor;
        sumaVal += nuevoValor;
        this.nValoraciones++;
        this.valor = sumaVal / ((double) this.nValoraciones);
    }
}
