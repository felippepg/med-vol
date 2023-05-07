package med.voll.api.medico;

public record DadosCadastroMedico(String nome, String email, String telefone, String CRM, Especialidade especialidade, DadosEndereco endereco) {
}
