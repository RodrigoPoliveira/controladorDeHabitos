package br.com.fiap.ControladorHabitos.controller;

import br.com.fiap.ControladorHabitos.dto.HabitoDTO;
import br.com.fiap.ControladorHabitos.service.HabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/habito")
public class HabitoController {

    @Autowired
    private HabitoService service;

    @GetMapping
    public ResponseEntity<Collection<HabitoDTO>> findAll() {
        var habitos = service.findAll();
        return ResponseEntity.ok(habitos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitoDTO> findById(@PathVariable Integer id) {
        var habito = service.findById(id);
        return ResponseEntity.ok(habito);
    }

    @PostMapping
    public ResponseEntity<HabitoDTO> save(@RequestBody HabitoDTO habitoDTO) {
        habitoDTO = service.save(habitoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(habitoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HabitoDTO> update(@PathVariable Integer id, @RequestBody HabitoDTO habitoDTO) {
        habitoDTO = service.update(id, habitoDTO);
        return ResponseEntity.ok(habitoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
