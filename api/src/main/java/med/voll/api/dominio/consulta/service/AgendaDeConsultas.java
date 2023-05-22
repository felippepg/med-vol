package med.voll.api.dominio.consulta.service;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.*;
import med.voll.api.dominio.consulta.valicaoes.cancelamento.ValidacaoHorarioCancelamentoDeConsultas;
import med.voll.api.dominio.consulta.valicaoes.agendamento.ValidacoesAgendarConsultas;
import med.voll.api.dominio.medico.Medico;
import med.voll.api.dominio.medico.MedicoRepository;
import med.voll.api.dominio.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    private List<ValidacoesAgendarConsultas> validacoesAgendarConsultas;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ValidacaoHorarioCancelamentoDeConsultas validacaoHorarioCancelamentoDeConsultas;


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.pacienteId())) {
            throw new ValidacaoException("Não foi possivel encontrar o paciente!");
        }

        if (dados.medicoId() != null && !medicoRepository.existsById(dados.medicoId())) {
            throw new ValidacaoException("Não foi possivel encontrar o médico!");
        }


        validacoesAgendarConsultas.stream()
                .forEach(consulta -> consulta.validar(dados));

        var medico = buscarMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        var paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        var consulta = new Consulta(null, medico, paciente, dados.data(), null);
        consultaRepository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico buscarMedico(DadosAgendamentoConsulta dados) {
        if(dados.medicoId() != null) {
            return medicoRepository.getReferenceById(dados.medicoId());
        }

        if(dados.especialidade() == null) {
            throw new ValidacaoException("Necessário informar a especiaalidade do médico!");
        }

        return medicoRepository.buscarMedicoAleatorio(dados.especialidade(), dados.data());
    }

    public void desmarcar(DadosDesmarcarConsulta dados) {
        validacaoHorarioCancelamentoDeConsultas.validar(dados);
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
