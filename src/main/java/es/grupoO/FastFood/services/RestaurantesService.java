package es.grupoO.FastFood.services;
import es.grupoO.FastFood.dto.RestauranteLoginDTO;
import es.grupoO.FastFood.factory.RestauranteFactory;
import es.grupoO.FastFood.mapper.RestauranteLoginMapper;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.entity.Valoracion;
import es.grupoO.FastFood.model.state.CategoriaRestaurante;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.model.valueobject.Posicion;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import es.grupoO.FastFood.repository.ValoracionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import es.grupoO.FastFood.auth.HashMaker;
import org.springframework.security.core.Authentication;
import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.exceptions.NotValidEmailException;
import es.grupoO.FastFood.exceptions.UsernameAlreadyExistException;
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

    public Page<Restaurante> buscarRestaurante(String nombre, Pageable paginacion) {
        Page<Restaurante> restaurantes = this.repository.findAllByNombreContainingPaginado(nombre, paginacion);
        return restaurantes;
    }

    public Page<Restaurante> buscarRestaurante(int categoria, Pageable paginacion) {
        CategoriaRestaurante cat = CategoriaRestaurante.fromInteger(categoria);
        Page<Restaurante> restaurantes = this.repository.findAllByCategoriaPaginado(cat, paginacion);
        return restaurantes;
    }

    public Restaurante insertarRestaurante(String nombre, int categoria, String direccion,String telefono, 
        String email, String horaApertura, String horaCierre, String passwd){

        Email parsedEmail = Email.parse(email)
                .orElseThrow(() -> new NotValidEmailException("El email proporcionado no es correcto"));

        if(this.repository.findByEmail(parsedEmail) != null) {
            throw new UsernameAlreadyExistException("El restaurante ya existe");
        }

        RestauranteFactory fact = new RestauranteFactory(nombre, direccion, telefono, horaApertura, horaCierre, categoria, email, passwd);
        Restaurante restaurante = fact.fabricarRestaurante();
        Valoracion val = restaurante.getValoracion();
        this.valoracionesRepository.save(val);
        this.repository.save(restaurante);
        return restaurante;
    }

    public void borrarRestaurante(Authentication auth) {
        Email email = Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("Email no valido"));
        Restaurante restaurante = this.repository.findByEmail(email);
        if(restaurante == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        this.repository.delete(restaurante);
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
        Email email = Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("El email proporcionado no es correcto"));
        //no hay que comprobar el email, porque el token ya ha sido validado.
        Restaurante restaurante = this.repository.findByEmail(email);
        restaurante.setHashPassword(hasher.encoder(newPasswd));
        this.repository.save(restaurante);
    }

    public Page<Restaurante> buscarRestaurantesCercanos(Posicion posicionRepartidor, Pageable pageable) {
        return this.repository.findByPosicionNear(posicionRepartidor, pageable);
    }
}
