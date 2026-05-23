package es.grupoO.FastFood.services;

import es.grupoO.FastFood.auth.HashMaker;
import es.grupoO.FastFood.exceptions.NotValidEmailException;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.model.valueobject.Pair;
import es.grupoO.FastFood.repository.ClientesRepository;
import es.grupoO.FastFood.repository.RepartidoresRepository;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import es.grupoO.FastFood.exceptions.NoExistDBException;
import es.grupoO.FastFood.exceptions.NoMatchingPasswordException;

@Service
public class AuthService {
    @Autowired
    private final ClientesRepository clientesRepository;
    @Autowired
    private final RestaurantesRepository restaurantesRepository;
    @Autowired
    private final RepartidoresRepository repartidoresRepository;

    // Clave secreta (debe tener mínimo 256 bits)
    private static final SecretKey key = Keys.hmacShaKeyFor("MiClaveSecretaSuperSeguraYMuyLarga2026!".getBytes());

    public AuthService(ClientesRepository clientesRepository, RestaurantesRepository restaurantesRepository, RepartidoresRepository repartidoresRepository) {
        this.clientesRepository = clientesRepository;
        this.restaurantesRepository = restaurantesRepository;
        this.repartidoresRepository = repartidoresRepository;
    }

    /**
     * @param email
     * @param password
     * @return el cliente + token JWT
     */
    public Pair<Cliente, String> loginCliente(String email, String password) {
        Email parsedEmail = Email.parse(email)
                .orElseThrow(() -> new NotValidEmailException("El email del cliente no es valido"));
        Cliente cliente = this.clientesRepository.findByEmail(parsedEmail);

       if(cliente == null) {
           throw new NoExistDBException("El cliente no esta registrado");
       }

        String hashedPasswd = cliente.gethashPassword();
        comprobarPassword(password, hashedPasswd);

        return new Pair<>(cliente, this.getJWTToken(email, "CLIENTE"));
    }

    /**
     * @param email
     * @param password
     * @return el restaurante + token JWT
     */
    public Pair<Restaurante, String> loginRestaurante(String email, String password) {
        Email parsedEmail = Email.parse(email)
                .orElseThrow(() -> new NotValidEmailException("El email del restaurante no es valido"));
        Restaurante rest = this.restaurantesRepository.findByEmail(parsedEmail);

        if(rest == null) {
            throw new NoExistDBException("El restaurante no esta registrado");
        }

        String hashedPasswd = rest.gethashPassword();
        comprobarPassword(password, hashedPasswd);

        return new Pair<>(rest, this.getJWTToken(email, "RESTAURANTE"));
    }
    /**
     * @param email
     * @param password
     * @return el repartidor + token JWT
     */
    public Pair<Repartidor, String> loginRepartidor(String email, String password) {
        Email parsedEmail = Email.parse(email)
                .orElseThrow(() -> new NotValidEmailException("El email del repartidor no es valido"));
        Repartidor repartidor = this.repartidoresRepository.findByEmail(parsedEmail);

        if(repartidor == null) {
            throw new NoExistDBException("El email introducido no corresponde a ningun usuario");
        }

        String hashedPasswd = repartidor.getHashPassword();
        comprobarPassword(password, hashedPasswd);

        return new Pair<>(repartidor, this.getJWTToken(email, "REPARTIDOR"));
    }

    /**
     *
     * @param password
     * @param hashedPasswd
     * @throws NoMatchingPasswordException si la contraseña no coincide con el hash
     */
    private static void comprobarPassword(String password, String hashedPasswd) {
        HashMaker hasher = new HashMaker();

        if(!hasher.verify(hashedPasswd, password)) {
            throw new NoMatchingPasswordException("Contraseña incorreta");
        }
    }

    /**
     * @param username
     * @param role
     * @return token JWT
     */
    private String getJWTToken(String username, String role) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(role);

        String token = Jwts
            .builder()
            .id("FastFoodV1")
            .subject(username)
            .claim("authorities",
                    grantedAuthorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 600000))
            .signWith(this.key).compact();

        return "Bearer " + token;
    }

    public static SecretKey getKey() {
        return key;
    }
}
