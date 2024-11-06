package br.com.fiap.ControladorHabitos.service;

import br.com.fiap.ControladorHabitos.dto.MentoradoDTO;
import br.com.fiap.ControladorHabitos.entity.Mentorado;
import br.com.fiap.ControladorHabitos.exception.ControllerNotFoundException;
import br.com.fiap.ControladorHabitos.repository.MentoradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MentoradoService {

    @Autowired
    private MentoradoRepository mentoradoRepository;

    public Collection<MentoradoDTO> findAll() {
        var mentorados = mentoradoRepository.findAll();
        return mentorados.stream().map(this::toMentoradoDto).collect(Collectors.toList());
    }

    public MentoradoDTO findById(Integer id) {
        var mentorado = mentoradoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException(String.format("Mentorado [%s] não encontrado", id)));
        return toMentoradoDto(mentorado);
    }

    public MentoradoDTO save(MentoradoDTO mentoradoDTO) {
        var mentorado = mentoradoRepository.save(toMentorado(mentoradoDTO));
        return toMentoradoDto(mentorado);
    }

    public MentoradoDTO update(Integer id, MentoradoDTO mentoradoDTO) {
        try {
            Mentorado mentorado = mentoradoRepository.getReferenceById(id);
            mentorado.setNome(mentoradoDTO.nome());
            mentorado = mentoradoRepository.save(mentorado);

            return toMentoradoDto(mentorado);
        } catch (ControllerNotFoundException e) {
            throw new ControllerNotFoundException(String.format("Mentorado [%s] não encontrado", id));
        }
    }

    private MentoradoDTO toMentoradoDto(Mentorado mentorado) {
        return new MentoradoDTO(
                mentorado.getId(),
                mentorado.getNome()
        );
    }

    private Mentorado toMentorado(MentoradoDTO mentoradoDTO) {
        return new Mentorado(
                mentoradoDTO.id(),
                mentoradoDTO.nome()
        );
    }
}
