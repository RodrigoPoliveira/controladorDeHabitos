package br.com.fiap.ControladorHabitos.repository;

import br.com.fiap.ControladorHabitos.entity.Habito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitoRepository extends JpaRepository<Habito, Integer> {

    Optional<Habito> findByNomeIgnoreCase(String nome);
}
