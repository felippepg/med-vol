package med.voll.api.dominio.consulta.valicaoes.cancelamento;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.ConsultaRepository;
import med.voll.api.dominio.consulta.DadosDesmarcarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidacaoHorarioCancelamentoDeConsultas implements ValidacoesDesmarcarConsultas{
    @Autowired
    ConsultaRepository consultaRepository;

    public void validar(DadosDesmarcarConsulta dados) {
        if(!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Não foi possível localizar a consulta");
        }
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        var diferenca = Duration.between(LocalDateTime.now(), consulta.getData()).toHours();
        if(diferenca < 24) {
            throw new ValidacaoException("A consulta só pode ser desmarcada com 24 horas de antecedência");
        }
    }
}
