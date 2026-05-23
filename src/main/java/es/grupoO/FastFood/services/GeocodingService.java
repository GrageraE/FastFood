package es.grupoO.FastFood.services;

import es.grupoO.FastFood.dto.NominatimResponse;
import es.grupoO.FastFood.model.valueobject.Posicion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Posicion obtenerCoordenadas(String direccion) {

        String url = "https://nominatim.openstreetmap.org/search"
                + "?q=" + direccion
                + "&format=json&limit=1";

        ResponseEntity<NominatimResponse[]> response =
                restTemplate.getForEntity(url, NominatimResponse[].class);

        NominatimResponse[] body = response.getBody();

        if (body == null || body.length == 0) {
            throw new RuntimeException("Dirección no encontrada");
        }

        double lat = Double.parseDouble(body[0].getLat());
        double lon = Double.parseDouble(body[0].getLon());

        return new Posicion(lat, lon);
    }
}