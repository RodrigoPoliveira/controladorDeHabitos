package br.com.fiap.ControladorHabitos.repository;

import br.com.fiap.ControladorHabitos.entity.Planner;
import br.com.fiap.ControladorHabitos.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {

    Turma findByPlanner(Planner planner);

    Optional<Turma> findByMentorados_Id(Integer mentoradoId);
}
