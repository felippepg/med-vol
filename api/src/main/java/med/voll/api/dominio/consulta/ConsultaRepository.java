package med.voll.api.dominio.consulta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("""
            SELECT c FROM Consulta c 
            WHERE c.paciente.id = :id
            AND c.data = :data            
            """)
    Consulta buscaConsultaNoDia(Long id, LocalDateTime data);

    Boolean existsByMedicoIdAndData(Long id, LocalDateTime data);

    Boolean existsByPacienteIdAndData(Long id, LocalDateTime data);
}
