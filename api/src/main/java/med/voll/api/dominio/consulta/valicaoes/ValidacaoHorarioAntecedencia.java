package med.voll.api.dominio.consulta.valicaoes;

import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidacaoHorarioAntecedencia implements ValidacoesAgendamentoConsultas {
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferenca = Duration.between(dataConsulta, agora).toMinutes();

        if(diferenca < 30) {
            throw new RuntimeException("A consulta deve ser agendada com 30 minutos de antecedência no mínimo!");
        }
    }
}
