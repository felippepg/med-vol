package med.voll.api.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.dominio.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JWTService jwtService;

    @Autowired
    UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = getTokenJWT(request);
        System.out.println(tokenJWT);
        if(tokenJWT != null) {
            var subject = jwtService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject);
            var authentication =
                    new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


    private String getTokenJWT(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if(token != null) {
            return token.replace("Bearer ", "");
        }
        return null;
    }
}
