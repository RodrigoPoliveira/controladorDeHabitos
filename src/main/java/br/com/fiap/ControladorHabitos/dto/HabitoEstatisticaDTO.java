package br.com.fiap.ControladorHabitos.dto;

public record HabitoEstatisticaDTO(
        String habito,
        Integer qtdDiasRealizado,
        String aproveitamento
) {
}
