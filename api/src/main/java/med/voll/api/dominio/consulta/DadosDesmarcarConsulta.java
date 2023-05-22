package med.voll.api.dominio.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record DadosDesmarcarConsulta(
        @NotBlank
        @JsonAlias("id_consulta")
        Long idConsulta,
        @NotBlank
        @JsonAlias("motivo_cancelamento")
        Motivo motivo) {
}
