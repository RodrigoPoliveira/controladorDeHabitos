package br.com.fiap.ControladorHabitos.controller;

import br.com.fiap.ControladorHabitos.dto.PlannerEstatisticaDTO;
import br.com.fiap.ControladorHabitos.service.EstatisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    @Autowired
    private EstatisticaService estatisticaService;

    @GetMapping("/mentorado/{mentoradoId}")
    public ResponseEntity<PlannerEstatisticaDTO> getPlannerEstatisticaMentorado(@PathVariable Integer mentoradoId) {
        var estatistica = estatisticaService.getEstatisticasPlannerMentorado(mentoradoId);
        return ResponseEntity.ok(estatistica);
    }
}
