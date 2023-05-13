package med.voll.api.paciente;

import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;

public record DadosAtualizarPaciente(Long id, String nome, String telefone, DadosEndereco endereco) {
}
