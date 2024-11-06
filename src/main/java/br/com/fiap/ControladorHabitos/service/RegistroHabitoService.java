package br.com.fiap.ControladorHabitos.service;

import br.com.fiap.ControladorHabitos.dto.RegistroHabitoDTO;
import br.com.fiap.ControladorHabitos.entity.*;
import br.com.fiap.ControladorHabitos.exception.BusinessValidationException;
import br.com.fiap.ControladorHabitos.exception.ControllerNotFoundException;
import br.com.fiap.ControladorHabitos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;

@Service
public class RegistroHabitoService {

    @Autowired
    private RegistroHabitoRepository registroHabitoRepository;
    @Autowired
    private MentoradoRepository mentoradoRepository;
    @Autowired
    private PlannerRepository plannerRepository;
    @Autowired
    private HabitoRepository habitoRepository;
    @Autowired
    private TurmaRepository turmaRepository;

    public RegistroHabitoDTO save(RegistroHabitoDTO registroHabitoDTO) {
        var registroHabito = toRegistroHabito(registroHabitoDTO);

        boolean existRegistroHabito = registroHabitoRepository.existsByDataAndMentoradoAndPlannerAndHabito(
                registroHabito.getData(),
                registroHabito.getMentorado(),
                registroHabito.getPlanner(),
                registroHabito.getHabito()
        );

        if(existRegistroHabito)
            throw new BusinessValidationException("Um hábito já foi registrado com os dados informados");

       Turma turma = turmaRepository.findByMentorados_Id(registroHabito.getMentorado().getId())
               .orElseThrow(() -> new BusinessValidationException("Não foi possível registrar o hábito: o mentorado não possui uma turma."));

        LocalDate dataInicioTurma = turma.getDataInicio();
        LocalDate dataFimTurma = turma.getDataFim();
        LocalDate dataRegistroHabito = registroHabito.getData();

        if( !((dataRegistroHabito.isAfter(dataInicioTurma) || dataRegistroHabito.isEqual(dataInicioTurma)) &&
                (dataRegistroHabito.isBefore(dataFimTurma) || dataRegistroHabito.isEqual(dataFimTurma))) ) {

            String msgException = String.format("Não foi possível registrar o hábito: a data de registro de habito [%s] não corresponde ao periodo de datas de inicio e fim do planner [%s - %s]"
                    , dataRegistroHabito, dataInicioTurma, dataFimTurma);
            throw new BusinessValidationException(msgException);
        }

        if( !turma.getPlanner().equals(registroHabito.getPlanner()) ) {
            String msgException = String.format("Não foi possível registrar o hábito: o planner [%s] não pertence a turma do mentorado. O planner da turma é o %s",
                    registroHabito.getPlanner().getId(), turma.getPlanner().getId());
            throw new BusinessValidationException(msgException);
        }

        if ( !turma.getPlanner().getHabitos().contains(registroHabito.getHabito()) ) {
            String msgException = String.format("Não foi possível registrar o hábito: o hábito [%s] não pertence ao planner do mentorado",
                    registroHabito.getHabito().getId());
            throw new BusinessValidationException(msgException);
        }

        registroHabito = registroHabitoRepository.save(registroHabito);
        return toRegistroHabitoDto(registroHabito);
    }

    public Collection<RegistroHabitoDTO> findAll() {
        var registrosHabitos = registroHabitoRepository.findAll();
        return registrosHabitos.stream().map(this::toRegistroHabitoDto).toList();
    }

    public RegistroHabitoDTO findById(Integer id) {
        var registroHabito = registroHabitoRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException(String.format("Registro Hábito [%s] não localizado", id)));
        return toRegistroHabitoDto(registroHabito);
    }

    public void delete(Integer id) {
        registroHabitoRepository.deleteById(id);
    }

    private RegistroHabito toRegistroHabito(RegistroHabitoDTO registroHabitoDTO) {
        Mentorado mentorado = mentoradoRepository.findById(registroHabitoDTO.mentoradoId())
                .orElseThrow(() -> new ControllerNotFoundException(String.format("Mentorado [%s] não localizado", registroHabitoDTO.mentoradoId())));

        Planner planner = plannerRepository.findById(registroHabitoDTO.plannerId())
                .orElseThrow(() -> new ControllerNotFoundException(String.format("Planner [%s] não localizado", registroHabitoDTO.plannerId())));

        Habito habito = habitoRepository.findById(registroHabitoDTO.habitoId())
                .orElseThrow(() -> new ControllerNotFoundException(String.format("Hábito [%s] não localizado", registroHabitoDTO.habitoId())));

        return new RegistroHabito(
                registroHabitoDTO.id(),
                registroHabitoDTO.data(),
                mentorado,
                planner,
                habito
        );
    }

    private RegistroHabitoDTO toRegistroHabitoDto(RegistroHabito registroHabito) {
        return new RegistroHabitoDTO(
                registroHabito.getId(),
                registroHabito.getData(),
                registroHabito.getMentorado().getId(),
                registroHabito.getPlanner().getId(),
                registroHabito.getHabito().getId()
        );
    }
}
