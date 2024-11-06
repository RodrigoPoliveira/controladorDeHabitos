package br.com.fiap.ControladorHabitos.controller;

import br.com.fiap.ControladorHabitos.dto.TurmaDTO;
import br.com.fiap.ControladorHabitos.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/turma")
public class TurmaController {

    @Autowired
    private TurmaService turmaService;

    @PostMapping
    public ResponseEntity<TurmaDTO> save(@RequestBody TurmaDTO turmaDTO) {
        var turma = turmaService.save(turmaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(turma);
    }

    @GetMapping
    public ResponseEntity<Collection<TurmaDTO>> findAll() {
        var turmas = turmaService.findAll();
        return ResponseEntity.ok(turmas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaDTO> findById(@PathVariable Integer id) {
        var turma = turmaService.findById(id);
        return ResponseEntity.ok(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaDTO> update(@PathVariable Integer id, @RequestBody TurmaDTO turmaDTO) {
        var turma = turmaService.update(id, turmaDTO);
        return ResponseEntity.ok(turma);
    }
}
