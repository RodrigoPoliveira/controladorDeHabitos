package br.com.fiap.ControladorHabitos.service;

import br.com.fiap.ControladorHabitos.dto.HabitoDTO;
import br.com.fiap.ControladorHabitos.entity.Habito;
import br.com.fiap.ControladorHabitos.exception.ControllerNotFoundException;
import br.com.fiap.ControladorHabitos.repository.HabitoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class HabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    public Collection<HabitoDTO> findAll(){
        var habitos = habitoRepository.findAll();
        return habitos.stream().map(this::toHabitoDto).collect(Collectors.toList());
    }

    public HabitoDTO findById(Integer id) {
        var habito = habitoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException(String.format("Hábito [%s] não encontrado", id)));
        return toHabitoDto(habito);
    }

    public HabitoDTO save(HabitoDTO habitoDTO) {
        Habito habito = toHabito(habitoDTO);
        habito = habitoRepository.save(habito);
        return toHabitoDto(habito);
    }

    public HabitoDTO update(Integer id, HabitoDTO habitoDTO) {
        try {
            Habito buscaHabito = habitoRepository.getReferenceById(id);
            buscaHabito.setNome(habitoDTO.nome());
            buscaHabito = habitoRepository.save(buscaHabito);

            return toHabitoDto(buscaHabito);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException(String.format("Hábito [%s] não encontrado", id));
        }
    }

    public void delete(Integer id) {
        habitoRepository.deleteById(id);
    }

    private HabitoDTO toHabitoDto(Habito habito) {
        return new HabitoDTO(
                habito.getId(),
                habito.getNome()
        );
    }

    private Habito toHabito(HabitoDTO habitoDTO) {
        return new Habito(
                habitoDTO.id(),
                habitoDTO.nome()
        );
    }

}
