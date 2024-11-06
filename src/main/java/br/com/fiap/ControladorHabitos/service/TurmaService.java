package br.com.fiap.ControladorHabitos.service;

import br.com.fiap.ControladorHabitos.dto.TurmaDTO;
import br.com.fiap.ControladorHabitos.entity.Mentorado;
import br.com.fiap.ControladorHabitos.entity.Planner;
import br.com.fiap.ControladorHabitos.entity.Turma;
import br.com.fiap.ControladorHabitos.exception.ControllerNotFoundException;
import br.com.fiap.ControladorHabitos.repository.MentoradoRepository;
import br.com.fiap.ControladorHabitos.repository.PlannerRepository;
import br.com.fiap.ControladorHabitos.repository.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private MentoradoRepository mentoradoRepository;

    @Autowired
    private PlannerRepository plannerRepository;

    public TurmaDTO save(TurmaDTO turmaDTO) {
        var turma = toTurma(turmaDTO);
        turma = turmaRepository.save(turma);

        return toTurmaDto(turma);
    }

    public Collection<TurmaDTO> findAll() {
        var turmas = turmaRepository.findAll();
        return turmas.stream().map(this::toTurmaDto).toList();
    }

    public TurmaDTO findById(Integer id) {
        var turma = turmaRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException(String.format("Turma [%s] não localizado", id)));
        return toTurmaDto(turma);
    }

    public TurmaDTO update(Integer id, TurmaDTO turmaDTO) {
        var turmaConverted = toTurma(turmaDTO);

        try {
            var turma = turmaRepository.getReferenceById(id);
            turma.setNome(turmaConverted.getNome());
            turma.setDataInicio(turmaConverted.getDataInicio());
            turma.setDataFim(turmaConverted.getDataFim());
            turma.setPlanner(turmaConverted.getPlanner());
            turma.setMentorados(turmaConverted.getMentorados());
            turma = turmaRepository.save(turma);

            return toTurmaDto(turma);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException(String.format("Turma [%s] não localizado", id));
        }
    }

    private Turma toTurma(TurmaDTO turmaDTO) {
        Planner planner = plannerRepository.findById(turmaDTO.plannerId())
                .orElseThrow(() -> new ControllerNotFoundException(String.format("Planner [%s] não localizado", turmaDTO.plannerId())));

        List<Mentorado> mentorados = new ArrayList<>();
        turmaDTO.mentoradosId().forEach(m -> {
            var mentorado = mentoradoRepository.findById(m);
            mentorado.ifPresentOrElse(
                    mentorados::add,
                    () -> { throw new ControllerNotFoundException(String.format("Mentorado [%s] não localizado", m)); }
            );
        });

        return new Turma(
                turmaDTO.id(),
                turmaDTO.nome(),
                turmaDTO.dataInicio(),
                turmaDTO.dataFim(),
                planner,
                mentorados
        );
    }

    private TurmaDTO toTurmaDto(Turma turma) {
        List<Integer> mentoradosId = turma.getMentorados().stream().map(Mentorado::getId).toList();

        return new TurmaDTO(
                turma.getId(),
                turma.getNome(),
                turma.getDataInicio(),
                turma.getDataFim(),
                turma.getPlanner().getId(),
                mentoradosId
        );
    }
}
