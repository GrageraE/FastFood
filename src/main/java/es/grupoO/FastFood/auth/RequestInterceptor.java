package es.grupoO.FastFood.auth;

import es.grupoO.FastFood.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    @Autowired
    private final AuthService authService;

    public RequestInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. Extraer el token de la cabecera HTTP "Authorization"
        String authHeader = request.getHeader("Authorization");

        // TODO: No pedir autenticacion para el login

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Falta el token de autorizacion (Bearer Token)");
            return false; // Detiene la petición, no llega al controlador
        }

        String token = authHeader.substring(7);

        try {
            // 2. Validar el token y extraer los datos (Claims)
            Claims claims = Jwts.parser()
                    .verifyWith(authService.getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String path = request.getRequestURI();
            String rol = claims.get("rol", String.class);

            // 3. Control de acceso rudimentario basado en la URL
            // TODO: Administrar roles de endpoints
//            if (path.startsWith("/api/admin") && !"ADMIN".equals(rol)) {
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                response.getWriter().write("Acceso denegado: Se requiere rol ADMIN");
//                return false;
//            }
//
//            if (path.startsWith("/api/client") && !"CLIENT".equals(rol)) {
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                response.getWriter().write("Acceso denegado: Se requiere rol CLIENT");
//                return false;
//            }

            // Inyectar el usuario en los atributos de la petición por si el controlador lo necesita
            request.setAttribute("username", claims.getSubject());
            request.setAttribute("rol", rol);

            return true; // Token válido, permite continuar hacia el endpoint

        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token invalido o expirado");
            return false;
        }
    }
}
