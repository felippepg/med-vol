package med.voll.api.dominio.consulta.valicaoes;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import med.voll.api.dominio.medico.MedicoRepository;
import med.voll.api.dominio.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidacaoPacienteInativo implements ValidacoesAgendamentoConsultas {

    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        if(paciente.getAtivo() == false) {
            throw new ValidacaoException("O paciente da consulta foi excluído do sistema");
        }
    }
}
