package med.voll.api.dominio.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.dominio.consulta.Consulta;
import med.voll.api.dominio.endereco.DadosEndereco;
import med.voll.api.dominio.paciente.DadosCadastroPacientes;
import med.voll.api.dominio.paciente.Paciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //indica ao Spring que será testado um Repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //indica que será usado servidor de BD
@ActiveProfiles("test") //indica o profile do application-test.properties
class MedicoRepositoryTest {

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Deveria retornar Null quando não existir méddico disponível")
    void buscarMedicoAleatorioCenario1() {
        var especialidade = Especialidade.DERMATOLOGIA;
        var data = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)) //busca a próxima segunda feira
                .atTime(10, 0);

        var medico = cadastrarMedico("Joao", "joao@vollmed.com.br", "145872", especialidade);
        var paciente = cadastrarPaciente("Nestor", "nestor@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, data);
        var medicoDisponivel = medicoRepository.buscarMedicoAleatorio(especialidade, data);
        Assertions.assertThat(medicoDisponivel).isNull();
    }


    @Test
    @DisplayName("Deveria retornar o médico quando existir méddico disponível")
    void buscarMedicoAleatorioCenario2() {
        var especialidade = Especialidade.DERMATOLOGIA;
        var data = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY)) //busca a próxima segunda feira
                .atTime(10, 0);

        var medico = cadastrarMedico("Joao", "joao@vollmed.com.br", "145872", especialidade);

        var medicoDisponivel = medicoRepository.buscarMedicoAleatorio(especialidade, data);
        Assertions.assertThat(medicoDisponivel).isEqualTo(medico);
    }


    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade) {
        return entityManager.persist(new Medico(dadosMedico(nome, email, crm, especialidade)));
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        return entityManager.persist(new Paciente(dadosPaciente(nome, email, cpf)));
    }

    private Consulta cadastrarConsulta(Medico medico, Paciente paciente,LocalDateTime data) {
        return entityManager.persist(new Consulta(null, medico, paciente, data, null));
    }


    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPacientes dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPacientes(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }


    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );

    }
}
