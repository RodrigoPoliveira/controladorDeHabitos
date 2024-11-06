package br.com.fiap.ControladorHabitos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record RegistroHabitoDTO(
        Integer id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate data,
        Integer mentoradoId,
        Integer plannerId,
        Integer habitoId
) {
}
