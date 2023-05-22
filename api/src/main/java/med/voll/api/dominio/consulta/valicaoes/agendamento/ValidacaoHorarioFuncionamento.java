package med.voll.api.dominio.consulta.valicaoes.agendamento;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidacaoHorarioFuncionamento implements ValidacoesAgendarConsultas {
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var domingo = dados.data().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDoHorario = dados.data().getHour() < 7;
        var depoisDoHorario = dados.data().getHour() > 18;

        if(domingo || antesDoHorario || depoisDoHorario) {
            throw new ValidacaoException("Fora do horário de expediente da clínica");
        }
    }
}
