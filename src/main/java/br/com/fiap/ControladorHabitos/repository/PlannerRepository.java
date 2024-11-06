package br.com.fiap.ControladorHabitos.repository;

import br.com.fiap.ControladorHabitos.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlannerRepository extends JpaRepository<Planner, Integer> {
}
