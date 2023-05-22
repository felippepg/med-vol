package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import med.voll.api.dominio.consulta.DadosDetalhamentoConsulta;
import med.voll.api.dominio.consulta.service.AgendaDeConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("consulta")
public class ConsultaController {

    @Autowired
    AgendaDeConsultas agendaDeConsultas;
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoConsulta> agendarConsulta(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var consulta = agendaDeConsultas.agendar(dados);
        return ResponseEntity.ok(consulta);
    }
}
