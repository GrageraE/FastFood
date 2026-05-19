package es.grupoO.FastFood.services;
import es.grupoO.FastFood.dto.RestauranteLoginDTO;
import es.grupoO.FastFood.factory.RestauranteFactory;
import es.grupoO.FastFood.mapper.RestauranteLoginMapper;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Valoracion;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import es.grupoO.FastFood.repository.ValoracionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import es.grupoO.FastFood.auth.HashMaker;
import org.springframework.security.core.Authentication;
import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.model.valueobject.Email;

@Service
public class RestaurantesService {
    @Autowired
    private RestaurantesRepository repository;
    @Autowired
    private ValoracionesRepository valoracionesRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private RestauranteLoginMapper restauranteMapper;

    public RestauranteLoginDTO validar(String email, String passwd) {
        Pair<Restaurante, String> data = this.authService.loginRestaurante(email, passwd);
        return this.restauranteMapper.fromPair(data);
    }

    public Restaurante buscarRestaurantePorID(String idRest) {
        Restaurante rest = this.repository.findById(idRest).orElse(null);
        if(rest == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        return rest;
    }

    public List<Restaurante> buscarRestaurante(String nombre) {
        List<Restaurante> restaurantes = this.repository.findAllByNombreContaining(nombre);
        if(restaurantes == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        return restaurantes;
    }

    public Restaurante insertarRestaurante(String nombre, int categoria, String direccion,String telefono, 
        String email, String horaApertura, String horaCierre, String passwd){
        //TODO comprobar condiciones de insercion
        RestauranteFactory fact = new RestauranteFactory(nombre, direccion, telefono, horaApertura, horaCierre, categoria, email, passwd);
        Restaurante restaurante = fact.fabricarRestaurante();
        Valoracion val = restaurante.getValoracion();
        this.valoracionesRepository.save(val);
        this.repository.save(restaurante);
        return restaurante;
    }

    public void borrarRestaurante(String id) {
        Restaurante restaurante = this.buscarRestaurantePorID(id);
        if(restaurante == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        this.repository.deleteById(id);
    }

    public void actualizarValoracion(String id, int valor) {
        Restaurante restaurante = this.buscarRestaurantePorID(id);
        if(restaurante == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        
        Valoracion valoracion = restaurante.getValoracion();
        valoracion.actualizarValoracion(valor);
        
        this.valoracionesRepository.save(valoracion);
    }

    public void changePasswdRestaurante(String newPasswd, Authentication auth) {
        HashMaker hasher = new HashMaker();
        Email email = Email.parse(auth.getName());
        //no hay que comprobar el email, porque el token ya ha sido validado.
        Restaurante restaurante = this.repository.findByEmail(email);
        restaurante.setHashPassword(hasher.encoder(newPasswd));
        this.repository.save(restaurante);
    }
}
