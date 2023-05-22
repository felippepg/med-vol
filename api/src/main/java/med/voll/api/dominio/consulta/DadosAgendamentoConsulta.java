package med.voll.api.dominio.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dominio.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        @NotNull
        @JsonAlias("paciente_id")
        Long pacienteId,
        @JsonAlias("medico_id")
        Long medicoId,

        Especialidade especialidade,

        LocalDateTime data) {
}
