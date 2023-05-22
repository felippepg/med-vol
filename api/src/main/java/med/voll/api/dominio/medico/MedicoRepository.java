package med.voll.api.dominio.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
     Page<Medico>findAllByAtivoTrue(Pageable pageable);

     @Query("""
             select m from Medico m
                where m.especialidade = :especialidade
                and m.ativo = 1
                and m.id not in (
                    select c.medico.id from Consulta c 
                    where c.data = :data
                )
                order by rand()
                LIMIT 1
             """)
    Medico buscarMedicoAleatorio(Especialidade especialidade, LocalDateTime data);
}
