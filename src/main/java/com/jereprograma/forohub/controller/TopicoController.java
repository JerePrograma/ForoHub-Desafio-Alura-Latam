package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.TopicoRequest;
import com.jereprograma.forohub.dto.response.TopicoResponse;
import com.jereprograma.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {
    private final TopicoService service;

    public TopicoController(TopicoService service) {
        this.service = service;
    }

    /**
     * Obtiene un tópico por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> getTopicById(@PathVariable Long id) {
        TopicoResponse response = service.getTopicoById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para registrar un nuevo tópico.
     *
     * @param request Datos del tópico enviados en formato JSON.
     * @return Respuesta con los datos del tópico creado.
     */
    @PostMapping
    public ResponseEntity<TopicoResponse> createTopic(@RequestBody @Valid TopicoRequest request) {
        TopicoResponse response = service.createTopico(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un tópico existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicoRequest request) {
        TopicoResponse response = service.updateTopico(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un tópico por su ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        service.deleteTopico(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar tópicos con soporte para paginación y ordenación.
     *
     * @param pageable Información de paginación y ordenación.
     * @return Lista paginada de tópicos.
     */
    @GetMapping
    public ResponseEntity<List<TopicoResponse>> getAllTopics(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<TopicoResponse> response = service.getAllTopicos(pageable);
        return ResponseEntity.ok(response.getContent());
    }
}
