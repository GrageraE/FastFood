package es.grupoO.FastFood.services;
import es.grupoO.FastFood.model.valueobject.Precio;
import org.springframework.stereotype.Service;

@Service
public class PagosService {
    //No implementado, no hay API externa, requiere pago
    public String realizarPago(double cantidad) {
        return "No hay contrato";
    }
}
