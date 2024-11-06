package br.com.fiap.ControladorHabitos.controller;

import br.com.fiap.ControladorHabitos.dto.PlannerDTO;
import br.com.fiap.ControladorHabitos.service.PlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/planner")
public class PlannerController {

    @Autowired
    private PlannerService plannerService;

    @PostMapping
    public ResponseEntity<PlannerDTO> save(@RequestBody PlannerDTO plannerDTO) {
        var planner = plannerService.save(plannerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(planner);
    }

    @GetMapping
    public ResponseEntity<Collection<PlannerDTO>> findAll() {
        var planners = plannerService.findAll();
        return ResponseEntity.ok(planners);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlannerDTO> findById(@PathVariable Integer id) {
        var planner = plannerService.findById(id);
        return ResponseEntity.ok(planner);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlannerDTO> update(@PathVariable Integer id, @RequestBody PlannerDTO plannerDTO) {
        var planner = plannerService.update(id, plannerDTO);
        return ResponseEntity.ok(planner);
    }
}
