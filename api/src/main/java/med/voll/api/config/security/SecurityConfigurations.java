package med.voll.api.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Classe responsável por alterar o método de autenticação padrão do Spring.
 *
 * @author Felippe Pires
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    /**
     * Retorna o filtro de segurança personalizado para configuração do Spring Security.
     *
     * @param httpSecurity O objeto HttpSecurity utilizado para configurar a segurança.
     * @return O filtro de segurança personalizado.
     * @throws Exception Se ocorrer algum erro durante a configuração.
     *
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable() // Responsável por desabilitar ataques CSRF, já que os tokens cuidam disso.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Define a autenticação como Stateless.
                .and().build();
    }

    /**
     * Ensina o Spring a instanciar o AuthenticationManager.
     *
     * @return a criação do objeto AuthenticationManager..
     * @throws Exception Se ocorrer algum erro durante a configuração.
     *
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     *
     * Ensina o Spring que será utilizado o Bcrypt para Hash de senha
     *
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
