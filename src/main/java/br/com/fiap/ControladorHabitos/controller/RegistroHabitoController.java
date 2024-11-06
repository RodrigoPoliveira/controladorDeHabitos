package br.com.fiap.ControladorHabitos.controller;

import br.com.fiap.ControladorHabitos.dto.RegistroHabitoDTO;
import br.com.fiap.ControladorHabitos.service.RegistroHabitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/registroHabito")
public class RegistroHabitoController {

    @Autowired
    private RegistroHabitoService registroHabitoService;

    @PostMapping
    public ResponseEntity<RegistroHabitoDTO> save(@RequestBody RegistroHabitoDTO registroHabitoDTO) {
        var registroHabito = registroHabitoService.save(registroHabitoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroHabito);
    }

    @GetMapping
    public ResponseEntity<Collection<RegistroHabitoDTO>> findAll() {
        var registrosHabitos = registroHabitoService.findAll();
        return ResponseEntity.ok(registrosHabitos);
    }

    @GetMapping("/{id}")
    private ResponseEntity<RegistroHabitoDTO> findById(@PathVariable Integer id) {
        var rehistroHabito = registroHabitoService.findById(id);
        return ResponseEntity.ok(rehistroHabito);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Integer id) {
        registroHabitoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
