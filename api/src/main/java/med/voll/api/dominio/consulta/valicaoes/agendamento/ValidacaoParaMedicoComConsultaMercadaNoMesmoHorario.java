package med.voll.api.dominio.consulta.valicaoes.agendamento;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.ConsultaRepository;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoParaMedicoComConsultaMercadaNoMesmoHorario implements ValidacoesAgendarConsultas {

    @Autowired
    ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        Boolean consulta = consultaRepository.existsByMedicoIdAndDataAndMotivoIsNull(dados.pacienteId(), dados.data());
        if(consulta) {
            throw new ValidacaoException("O médico já possui consulta nesse dia!");
        }
    }
}
