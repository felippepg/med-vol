package med.voll.api.dominio.consulta.valicaoes;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.Consulta;
import med.voll.api.dominio.consulta.ConsultaRepository;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoAgendamentoParaMedicoComConsultaMercadaNoDia implements ValidacoesAgendamentoConsultas {

    @Autowired
    ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        Boolean consulta = consultaRepository.existsByMedicoIdAndData(dados.pacienteId(), dados.data());
        if(consulta) {
            throw new ValidacaoException("O médico já possui consulta nesse dia!");
        }
    }
}
