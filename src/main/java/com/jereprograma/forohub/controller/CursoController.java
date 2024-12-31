package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.CursoRequest;
import com.jereprograma.forohub.dto.response.CursoResponse;
import com.jereprograma.forohub.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> getAllCursos() {
        List<CursoResponse> cursos = cursoService.getAllCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> getCursoById(@PathVariable Long id) {
        CursoResponse response = cursoService.getCursoById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CursoResponse> createCurso(@RequestBody @Valid CursoRequest request) {
        CursoResponse response = cursoService.createCurso(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> updateCurso(@PathVariable Long id, @RequestBody @Valid CursoRequest request) {
        CursoResponse response = cursoService.updateCurso(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}
