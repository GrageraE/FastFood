package es.grupoO.FastFood.services;

import es.grupoO.FastFood.auth.HashMaker;
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

import io.jsonwebtoken.SignatureAlgorithm;
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
    private final static SecretKey key = Keys.hmacShaKeyFor("MiClaveSecretaSuperSeguraYMuyLarga2026!".getBytes());

    public AuthService(ClientesRepository clientesRepository, RestaurantesRepository restaurantesRepository, RepartidoresRepository repartidoresRepository) {
        this.clientesRepository = clientesRepository;
        this.restaurantesRepository = restaurantesRepository;
        this.repartidoresRepository = repartidoresRepository;
    }

    public Pair<Cliente, String> loginCliente(String email, String password) {
        Cliente cliente = this.clientesRepository.findByEmail(Email.parse(email));

       if(cliente == null) {
           throw new NoExistDBException("El cliente no esta registrado");
       }

        String hashedPasswd = cliente.gethashPassword();
        comprobarPassword(password, hashedPasswd);

        return new Pair<>(cliente, this.getJWTToken(email, "CLIENTE"));
    }

    public Pair<Restaurante, String> loginRestaurante(String email, String password) {
        Restaurante rest =  this.restaurantesRepository.findByEmail(Email.parse(email));
        if(rest == null) {
            throw new NoExistDBException("El restaurante no esta registrado");
        }

        String hashedPasswd = rest.gethashPassword();
        comprobarPassword(password, hashedPasswd);

        return new Pair<>(rest, this.getJWTToken(email, "RESTAURANTE"));
    }

    public Pair<Repartidor, String> loginRepartidor(String email, String password) {
        // TODO: puede petar
        Repartidor repartidor = this.repartidoresRepository.findByEmail(Email.parse(email));

        String hashedPasswd = repartidor.getHashPassword();
        comprobarPassword(password, hashedPasswd);

        return new Pair<>(repartidor, this.getJWTToken(email, "REPARTIDOR"));
    }

    private static void comprobarPassword(String password, String hashedPasswd) {
        HashMaker hasher = new HashMaker();

        if(!hasher.verify(hashedPasswd, password)) {
            throw new NoMatchingPasswordException("Contraseña incorreta");
        }
    }

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
