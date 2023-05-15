package med.voll.api.dominio.paciente;

import med.voll.api.dominio.endereco.DadosEndereco;

public record DadosAtualizarPaciente(Long id, String nome, String telefone, DadosEndereco endereco) {
}
