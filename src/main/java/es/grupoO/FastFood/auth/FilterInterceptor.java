package es.grupoO.FastFood.auth;

import es.grupoO.FastFood.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilterInterceptor extends OncePerRequestFilter {
    public FilterInterceptor() {}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain handler)
            throws IOException, ServletException {
        // 1. Extraer el token de la cabecera HTTP "Authorization" y el endpoint solicitado
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            SecurityContextHolder.clearContext();
            handler.doFilter(request, response);
            return;
        }

        String token = authHeader.substring("Bearer ".length());

        try {
            // 2. Validar el token y extraer los datos (Claims)
            Claims claims = Jwts.parser()
                    .verifyWith(AuthService.getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 3. Control de acceso
            List<String> roles = (List<String>) claims.get("authorities");

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    claims.getSubject(), null, roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Inyectar el usuario en los atributos de la petición por si el controlador lo necesita
//            request.setAttribute("id", claims.getSubject());
//            request.setAttribute("rol", rol);
            handler.doFilter(request, response);
        } catch (Exception e) {
            // TODO: Continuar con los filtros??
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalido o expirado");
            SecurityContextHolder.clearContext();
        }
    }
}
