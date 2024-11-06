package br.com.fiap.ControladorHabitos.repository;

import br.com.fiap.ControladorHabitos.dto.HabitoRelatorioDTO;
import br.com.fiap.ControladorHabitos.entity.Habito;
import br.com.fiap.ControladorHabitos.entity.Mentorado;
import br.com.fiap.ControladorHabitos.entity.Planner;
import br.com.fiap.ControladorHabitos.entity.RegistroHabito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RegistroHabitoRepository extends JpaRepository<RegistroHabito, Integer> {

    boolean existsByDataAndMentoradoAndPlannerAndHabito(
            LocalDate data,
            Mentorado mentorado,
            Planner planner,
            Habito habito);

    @Query(value = """
        SELECT 
            h.ID AS id,
            h.NOME AS habito,
            COUNT(rh.ID) AS qtdVezesFeito
        FROM 
            TB_REGISTRO_HABITO rh 
        LEFT JOIN 
            TB_HABITO h ON h.ID = rh.HABITO_ID 
        WHERE 
            rh.MENTORADO_ID = :mentoradoId 
        GROUP BY 
            h.NOME, h.ID
        """, nativeQuery = true)
    List<HabitoRelatorioDTO> findHabitosByMentoradoId(@Param("mentoradoId") Integer mentoradoId);
}
