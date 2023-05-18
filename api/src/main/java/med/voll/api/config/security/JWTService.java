package med.voll.api.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.dominio.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

@Service
public class JWTService {
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256("SENHASECRETA");
            String token = JWT.create()
                    .withIssuer("API voll.med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar o JWT", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDate.now().plusDays(2).atStartOfDay().toInstant(ZoneOffset.of("-03:00"));
    }
}
