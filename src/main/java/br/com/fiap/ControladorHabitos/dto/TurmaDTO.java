package br.com.fiap.ControladorHabitos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record TurmaDTO(
        Integer id,
        String nome,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataInicio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataFim,
        Integer plannerId,
        List<Integer> mentoradosId
) {
}
