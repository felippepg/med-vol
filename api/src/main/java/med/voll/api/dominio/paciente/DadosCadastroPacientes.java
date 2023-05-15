package med.voll.api.dominio.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.dominio.endereco.DadosEndereco;

public record DadosCadastroPacientes(
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        String cpf,
        @NotNull @Valid
        DadosEndereco endereco


) {
}
