package med.voll.api.dominio.consulta.service;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.Consulta;
import med.voll.api.dominio.consulta.ConsultaRepository;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import med.voll.api.dominio.consulta.DadosDetalhamentoConsulta;
import med.voll.api.dominio.consulta.valicaoes.ValidacoesAgendamentoConsultas;
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
    private List<ValidacoesAgendamentoConsultas> validacoesAgendamentoConsultas;

    @Autowired
    private ConsultaRepository consultaRepository;


    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.pacienteId())) {
            throw new ValidacaoException("Não foi possivel encontrar o paciente!");
        }

        if (dados.medicoId() != null && !medicoRepository.existsById(dados.medicoId())) {
            throw new ValidacaoException("Não foi possivel encontrar o médico!");
        }


        validacoesAgendamentoConsultas.stream()
                .forEach(consulta -> consulta.validar(dados));

        var medico = buscarMedico(dados);
        if (medico == null) {
            throw new ValidacaoException("Não existe médico disponível nessa data!");
        }
        var paciente = pacienteRepository.getReferenceById(dados.pacienteId());
        var consulta = new Consulta(null, medico, paciente, dados.data());
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
}
