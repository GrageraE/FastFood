package es.grupoO.FastFood.services;

import es.grupoO.FastFood.auth.HashMaker;
import es.grupoO.FastFood.model.entity.Cliente;
import es.grupoO.FastFood.model.entity.Repartidor;
import es.grupoO.FastFood.model.entity.Restaurante;
import es.grupoO.FastFood.model.valueobject.Email;
import es.grupoO.FastFood.repository.ClientesRepository;
import es.grupoO.FastFood.repository.RepartidoresRepository;
import es.grupoO.FastFood.repository.RestaurantesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {
    @Service
    public class SimpleAuthService {
        @Autowired
        private final ClientesRepository clientesRepository;
        @Autowired
        private final RestaurantesRepository restaurantesRepository;
        @Autowired
        private final RepartidoresRepository repartidoresRepository;

        // Clave secreta (debe tener mínimo 256 bits)
//        private final SecretKey key = Keys.hmacShaKeyFor("MiClaveSecretaSuperSeguraYMuyLarga2026!".getBytes());

        public SimpleAuthService(ClientesRepository clientesRepository, RestaurantesRepository restaurantesRepository, RepartidoresRepository repartidoresRepository) {
            this.clientesRepository = clientesRepository;
            this.restaurantesRepository = restaurantesRepository;
            this.repartidoresRepository = repartidoresRepository;
        }

        public String loginCliente(String email, String password) {
            Cliente cliente = this.clientesRepository.findByEmail(Email.parse(email));

            if(cliente == null) {
                // TODO
                return null;
            }

            HashMaker hasher = new HashMaker();
            if(!hasher.verify(cliente.gethashPassword(), password)) {
                //TODO
                return null;
            }

//            return generarToken(username, "ADMIN");
        }

        public String loginRestaurante(String email, String password) {
            //TODO puede petar
            Restaurante rest = this.restaurantesRepository.findByEmail(Email.parse(email));
            if(rest == null) {
                // TODO
                return null;
            }

            HashMaker hasher = new HashMaker();
            if(!hasher.verify(rest.gethashPassword(), password)) {
                //TODO
                return null;
            }

//            return generarToken(email, "CLIENT");
        }

        public String loginRepartidor(String email, String password) {
            // TODO: puede petar
            Repartidor repartidor = this.repartidoresRepository.findByEmail(Email.parse(email));

            HashMaker hasher = new HashMaker();
            if(!hasher.verify(repartidor.getHashPassword(), password)) {
                // TODO: verificar
                return null;
            }

//            return generarToken(email, "CLIENT");
        }

        private String generarToken(String sub, String rol) {
            return Jwts.builder()
                    .subject(sub)
                    .claim("rol", rol)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 horas
                    .signWith(key)
                    .compact();
        }

        public SecretKey getKey() {
            return this.key;
        }
    }

}
