package med.voll.api.dominio.medico;

public record DadosListagemMedico(Long id, String nome, String email, String CRM, Especialidade especialidade) {
    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCRM(), medico.getEspecialidade());
    }
}
