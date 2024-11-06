package br.com.fiap.ControladorHabitos.service;

import br.com.fiap.ControladorHabitos.dto.HabitoEstatisticaDTO;
import br.com.fiap.ControladorHabitos.dto.HabitoRelatorioDTO;
import br.com.fiap.ControladorHabitos.dto.PlannerEstatisticaDTO;
import br.com.fiap.ControladorHabitos.entity.Habito;
import br.com.fiap.ControladorHabitos.entity.Turma;
import br.com.fiap.ControladorHabitos.exception.BusinessValidationException;
import br.com.fiap.ControladorHabitos.repository.RegistroHabitoRepository;
import br.com.fiap.ControladorHabitos.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstatisticaService {

    @Autowired
    private TurmaRepository turmaRepository;
    @Autowired
    private RegistroHabitoRepository registroHabitoRepository;

    public PlannerEstatisticaDTO getEstatisticasPlannerMentorado(Integer mentoradoId) {
        List<HabitoRelatorioDTO> habitosRelatorio = registroHabitoRepository.findHabitosByMentoradoId(mentoradoId);

        Turma turma = turmaRepository.findByMentorados_Id(mentoradoId)
                .orElseThrow(() -> new BusinessValidationException("O mentorado não possui uma turma. Associe-o a uma turma para consultar suas estatisticas."));

        String nomePlanner = turma.getPlanner().getNome();
        LocalDate dataInicioTurma = turma.getDataInicio();
        LocalDate dataFimTurma = turma.getDataFim();
        Integer diasCorridosPlanner = Math.toIntExact(ChronoUnit.DAYS.between(dataInicioTurma, LocalDate.now()));
        List<HabitoEstatisticaDTO> habitosEstatisticaList = new ArrayList<>();

        List<Habito> habitosPlanner = turma.getPlanner().getHabitos();
        habitosPlanner.forEach(hp -> {
            var habitoEstatisticaDto = buildHabitoEstatisticaDto(hp, diasCorridosPlanner, habitosRelatorio);
            habitosEstatisticaList.add(habitoEstatisticaDto);
        });

        return new PlannerEstatisticaDTO(nomePlanner, dataInicioTurma, dataFimTurma, diasCorridosPlanner, habitosEstatisticaList);
    }

    private HabitoEstatisticaDTO buildHabitoEstatisticaDto(Habito habito, Integer diasCorridosPlanner, List<HabitoRelatorioDTO> habitosRelatorio) {
        Integer qtdVezesFeito = habitosRelatorio.stream()
                .filter(hr -> hr.getId().equals(habito.getId()))
                .findFirst()
                .map(HabitoRelatorioDTO::getQtdVezesFeito)
                .orElse(0);

        if(qtdVezesFeito > 0) {
            double aproveitamento = (qtdVezesFeito / (double) diasCorridosPlanner) * 100;
            String aproveitamentoStr = String.format("%.2f%%", aproveitamento);
            return new HabitoEstatisticaDTO(
                    habito.getNome(),
                    qtdVezesFeito,
                    aproveitamentoStr
            );
        }

        return new HabitoEstatisticaDTO(habito.getNome(), 0, "0%");
    }
}
