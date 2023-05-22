package med.voll.api.dominio.consulta;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta (Long id, Long pacienteId, Long medicoId, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getPaciente().getId(), consulta.getMedico().getId(), consulta.getData());
    }
}
