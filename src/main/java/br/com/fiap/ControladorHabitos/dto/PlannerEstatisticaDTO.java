package br.com.fiap.ControladorHabitos.dto;

import java.time.LocalDate;
import java.util.List;

public record PlannerEstatisticaDTO(
        String planner,
        LocalDate dataInicio,
        LocalDate dataFim,
        Integer diasCorridos,
        List<HabitoEstatisticaDTO> habitos
) {
}
