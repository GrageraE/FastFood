package es.grupoO.FastFood.model.valueobject;

import com.mongodb.client.model.geojson.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import es.grupoO.FastFood.exceptions.InvalidPositionException;

public class Posicion {
    private double latitud;
    private double longitud;

    public Posicion(double latitud, double longitud) {
        if(latitud < -90 || latitud > 90) {
            throw new InvalidPositionException("Posicion dada es invalida, revise su metodo de dar la localizacion");
        }
        if(longitud < -180 || longitud > 180) {
            throw new InvalidPositionException("Posicion dada es invalida, revise su metodo de dar la localizacion");
        }
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public GeoJsonPoint toGeoJson() {
        return new GeoJsonPoint(this.longitud, this.latitud);
    }
}
