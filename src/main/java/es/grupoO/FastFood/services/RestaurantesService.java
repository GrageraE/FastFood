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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    @Autowired
    private GeocodingService geocodingService;

    /**
     * Valida el restaurante y lo pasa a un dto a traves de un mapper
     * @param email
     * @param passwd
     * @return RestauranteLoginDTO
     */
    public RestauranteLoginDTO validar(String email, String passwd) {
        Pair<Restaurante, String> data = this.authService.loginRestaurante(email, passwd);
        return this.restauranteMapper.fromPair(data);
    }

    /**
     * Busca restaurante por ID, devuelve el restaurante
     * @param idRest
     * @return Restaurante
     */
    public Restaurante buscarRestaurantePorID(String idRest) {
        Restaurante rest = this.repository.findById(idRest).orElse(null);
        if(rest == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        return rest;
    }

    /**
     * Devuelve una pagina de restaurantes cuyo nombre contenga el string pasado por parametro, con la paginacion dada
     * @param nombre
     * @param paginacion
     * @return Page<Restaurante>
     */
    public Page<Restaurante> buscarRestaurante(String nombre, Pageable paginacion) {
        return this.repository.findAllByNombreContainingPaginado(nombre, paginacion);
    }

    /**
     * Devuelve una pagina de restaurantes de la categoria dada por parametro, con la paginacion dada
     * @param categoria
     * @param paginacion
     * @return Page<Restaurante>
     */
    public Page<Restaurante> buscarRestaurante(int categoria, Pageable paginacion) {
        CategoriaRestaurante cat = CategoriaRestaurante.fromInteger(categoria);
        return this.repository.findAllByCategoriaPaginado(cat, paginacion);
    }

    /**
     * Inserta un restaurante y devuelve su jwt y lo inserta en la base de datos asociado a restaurantes
     * @param nombre
     * @param categoria
     * @param direccion
     * @param telefono
     * @param email
     * @param horaApertura
     * @param horaCierre
     * @param passwd
     * @throws NotValidEmailException
     * @throws UsernameAlreadyExistException
     * @return Restaurante
     */
    public Restaurante insertarRestaurante(String nombre, int categoria, String direccion,String telefono, 
        String email, String horaApertura, String horaCierre, String passwd){

        Email parsedEmail = Email.parse(email)
                .orElseThrow(() -> new NotValidEmailException("El email proporcionado no es correcto"));

        if(this.repository.findByEmail(parsedEmail) != null) {
            throw new UsernameAlreadyExistException("El restaurante ya existe");
        }

        RestauranteFactory fact = new RestauranteFactory(
                nombre, direccion, telefono, horaApertura,
                horaCierre, categoria, email, passwd,
                this.geocodingService
        );
        Restaurante restaurante = fact.fabricarRestaurante();
        Valoracion val = restaurante.getValoracion();
        this.valoracionesRepository.save(val);
        this.repository.save(restaurante);
        return restaurante;
    }

    /**
     * Borra el restaurante, se tiene que poseer el JWT y solo se puede borrar el restaurante asociado a ese JWT
     * @param auth
     * @throws NotValidEmailException
     */
    public void borrarRestaurante(Authentication auth) {
        Email email = Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("Email no valido"));
        Restaurante restaurante = this.repository.findByEmail(email);
        if(restaurante == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        this.repository.delete(restaurante);
    }

    /**
     * actaualiza la valoracion al dado, el calculo es interno
     * @param id
     * @param valor
     * @trhrows NoExistDBException
     */
    public void actualizarValoracion(String id, int valor) {
        Restaurante restaurante = this.buscarRestaurantePorID(id);
        if(restaurante == null) {
            throw new NoExistDBException("El restaurante no existe");
        }
        
        Valoracion valoracion = restaurante.getValoracion();
        valoracion.actualizarValoracion(valor);
        
        this.valoracionesRepository.save(valoracion);
    }

    /**
     * Cambia la contraseña del restaurante, se tiene que poseer el JWT y solo se puede modificar la contraseña del restaurante asociado a ese JWT
     * @param newPasswd
     * @param auth
     */
    public void changePasswdRestaurante(String newPasswd, Authentication auth) {
        HashMaker hasher = new HashMaker();
        Email email = Email.parse(auth.getName())
                .orElseThrow(() -> new NotValidEmailException("El email proporcionado no es correcto"));
        //no hay que comprobar el email, porque el token ya ha sido validado.
        Restaurante restaurante = this.repository.findByEmail(email);
        restaurante.setHashPassword(hasher.encoder(newPasswd));
        this.repository.save(restaurante);
    }

    /**
     * Devuelve una pageable de los restaurantes mas cercanos, dada una ubicacion
     * @param posicionRepartidor
     * @param pageable
     * @return Page<Restaurante>
     */
    public Page<Restaurante> buscarRestaurantesCercanos(Posicion posicionRepartidor, Pageable pageable) {
        return this.repository.findByPosicionNear(posicionRepartidor, pageable);
    }
}
