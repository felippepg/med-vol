package med.voll.api.dominio.consulta.valicaoes.agendamento;

import med.voll.api.config.exception.ValidacaoException;
import med.voll.api.dominio.consulta.DadosAgendamentoConsulta;
import med.voll.api.dominio.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidacaoMedicoInativo implements ValidacoesAgendarConsultas {

    @Autowired
    MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        if(dados.medicoId() == null) {
            return;
        }
        var medico = medicoRepository.getReferenceById(dados.medicoId());
        if(medico.getAtivo() == false) {
            throw new ValidacaoException("O médico responsável pela consulta foi excluído do sistema");
        }
    }
}
