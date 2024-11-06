package br.com.fiap.ControladorHabitos.service;

import br.com.fiap.ControladorHabitos.dto.PlannerDTO;
import br.com.fiap.ControladorHabitos.entity.Habito;
import br.com.fiap.ControladorHabitos.entity.Planner;
import br.com.fiap.ControladorHabitos.exception.ControllerNotFoundException;
import br.com.fiap.ControladorHabitos.repository.HabitoRepository;
import br.com.fiap.ControladorHabitos.repository.PlannerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PlannerService {

    @Autowired
    private PlannerRepository plannerRepository;

    @Autowired
    private HabitoRepository habitoRepository;

    public PlannerDTO save(PlannerDTO plannerDTO) {
        var planner = toPlanner(plannerDTO);
        planner = plannerRepository.save(planner);
        return toPlannerDto(planner);
    }

    public Collection<PlannerDTO> findAll() {
        var planners = plannerRepository.findAll();
        return planners.stream().map(this::toPlannerDto).toList();
    }

    public PlannerDTO findById(Integer id) {
        var planner = plannerRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException(String.format("Planner [%s] não localizado", id)));
        return toPlannerDto(planner);
    }

    public PlannerDTO update(Integer id, PlannerDTO plannerDTO) {
        var plannerConverted = toPlanner(plannerDTO);
        try {
            var planner = plannerRepository.getReferenceById(id);
            planner.setNome(plannerConverted.getNome());
            planner.setHabitos(plannerConverted.getHabitos());
            planner = plannerRepository.save(planner);

            return toPlannerDto(planner);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException(String.format("Planner [%s] não localizado", id));
        }
    }

    private Planner toPlanner(PlannerDTO plannerDTO) {
        List<Habito> habitos = new ArrayList<>();

        plannerDTO.habitos().forEach(h -> {
            var habito = habitoRepository.findByNomeIgnoreCase(h);
            habito.ifPresentOrElse(
                    habitos::add,
                    () -> { throw new ControllerNotFoundException(String.format("Hábito '%s' não localizado", h)); }
            );
        });

        return new Planner(
                plannerDTO.id(),
                plannerDTO.nome(),
                habitos
        );
    }

    private PlannerDTO toPlannerDto(Planner planner) {
        List<String> habitos = planner.getHabitos().stream().map(Habito::getNome).toList();
        return new PlannerDTO(
                planner.getId(),
                planner.getNome(),
                habitos
        );
    }
}
