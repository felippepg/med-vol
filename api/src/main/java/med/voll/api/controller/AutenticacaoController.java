package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.config.security.JWTService;
import med.voll.api.dominio.usuario.DadosAutenticacao;
import med.voll.api.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class AutenticacaoController {

    /***
     *  O atributo authenticationManager chama por debaixo dos panos o AutenticaçãoService, que é responsável por fazer
     *  o login
     */
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        //DTO do proprio Spring
        var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var usuarioAutenticado = authenticationManager.authenticate(token);

        return ResponseEntity.ok(jwtService.gerarToken((Usuario) usuarioAutenticado.getPrincipal()));
    }
}
