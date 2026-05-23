package es.grupoO.FastFood.services;

import es.grupoO.FastFood.exceptions.GeocodingException;
import es.grupoO.FastFood.model.valueobject.Posicion;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;

import java.net.URI;

@Service
public class GeocodingService {

    private final RestClient restClient;

    public GeocodingService() {
        this.restClient = RestClient.builder()
                .baseUrl(URI.create("https://nominatim.openstreetmap.org/search"))
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36 Edg/148.0.0.0")
                .build();
    }

    public Posicion obtenerCoordenadas(String direccion) {
        Posicion posicion;
        try {
            JsonNode resp = this.restClient
                    .get()
                    .uri("?q={direccion}&format=geojson&limit=1", direccion)
                    .retrieve()
                    .body(JsonNode.class);

            ArrayNode resultados = resp.path("features").asArray();

            if(resultados.isEmpty()) {
                throw new GeocodingException("Sin resultados");
            }

            ArrayNode ptoJson = resultados.get(0).path("geometry").path("coordinates").asArray();
            double longitud = ptoJson.get(0).asDouble();
            double latitud = ptoJson.get(1).asDouble();

            posicion = new Posicion(latitud, longitud);
        } catch (HttpClientErrorException e) {
            throw new GeocodingException("Error: " + e.getMessage());
        }

        return posicion;
    }
}