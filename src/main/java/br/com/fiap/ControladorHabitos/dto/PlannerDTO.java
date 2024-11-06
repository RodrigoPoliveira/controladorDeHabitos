package br.com.fiap.ControladorHabitos.dto;

import java.util.List;

public record PlannerDTO(
        Integer id,
        String nome,
        List<String> habitos
) {
}
