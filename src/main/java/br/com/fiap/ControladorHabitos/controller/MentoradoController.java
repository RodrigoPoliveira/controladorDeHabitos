package br.com.fiap.ControladorHabitos.controller;

import br.com.fiap.ControladorHabitos.dto.MentoradoDTO;
import br.com.fiap.ControladorHabitos.service.MentoradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/mentorado")
public class MentoradoController {

    @Autowired
    private MentoradoService mentoradoService;

    @GetMapping
    public ResponseEntity<Collection<MentoradoDTO>> findAll() {
        var mentorados = mentoradoService.findAll();
        return ResponseEntity.ok(mentorados);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentoradoDTO> findById(@PathVariable Integer id) {
        var mentorado = mentoradoService.findById(id);
        return ResponseEntity.ok(mentorado);
    }

    @PostMapping
    public ResponseEntity<MentoradoDTO> save(@RequestBody MentoradoDTO mentoradoDTO) {
        mentoradoDTO = mentoradoService.save(mentoradoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(mentoradoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentoradoDTO> update(@PathVariable Integer id, @RequestBody MentoradoDTO mentoradoDTO) {
        mentoradoDTO = mentoradoService.update(id, mentoradoDTO);
        return ResponseEntity.ok(mentoradoDTO);
    }
}
