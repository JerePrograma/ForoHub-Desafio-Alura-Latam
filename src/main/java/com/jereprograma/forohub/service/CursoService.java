package com.jereprograma.forohub.service;

import com.jereprograma.forohub.dto.request.CursoRequest;
import com.jereprograma.forohub.dto.response.CursoResponse;
import com.jereprograma.forohub.model.Curso;
import com.jereprograma.forohub.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoResponse> getAllCursos() {
        return cursoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CursoResponse getCursoById(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));
        return mapToResponse(curso);
    }

    public CursoResponse createCurso(CursoRequest request) {
        if (cursoRepository.existsByNombre(request.nombre())) {
            throw new RuntimeException("Ya existe un curso con el nombre: " + request.nombre());
        }

        Curso curso = new Curso();
        curso.setNombre(request.nombre());
        curso.setCategoria(request.categoria());
        curso.setStatus(request.status());

        Curso savedCurso = cursoRepository.save(curso);
        return mapToResponse(savedCurso);
    }

    public CursoResponse updateCurso(Long id, CursoRequest request) {
        Curso existingCurso = cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + id));

        existingCurso.setNombre(request.nombre());
        existingCurso.setCategoria(request.categoria());
        existingCurso.setStatus(request.status());

        Curso updatedCurso = cursoRepository.save(existingCurso);
        return mapToResponse(updatedCurso);
    }

    public void deleteCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado con ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

    private CursoResponse mapToResponse(Curso curso) {
        return new CursoResponse(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria(),
                curso.getStatus()
        );
    }
}
