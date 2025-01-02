package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.CursoRequest;
import com.jereprograma.forohub.dto.response.CursoResponse;
import com.jereprograma.forohub.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Listar todos los cursos", description = "Devuelve una lista de todos los cursos disponibles.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<CursoResponse>> listarCursos() {
        return ResponseEntity.ok(cursoService.getAllCursos());
    }

    @Operation(summary = "Obtener un curso por ID", description = "Devuelve los detalles de un curso específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> obtenerCursoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.getCursoById(id));
    }

    @Operation(summary = "Crear un curso", description = "Registra un nuevo curso en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<CursoResponse> crearCurso(@RequestBody @Valid CursoRequest solicitud) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.createCurso(solicitud));
    }

    @Operation(summary = "Actualizar un curso", description = "Modifica los datos de un curso existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CursoResponse> actualizarCurso(
            @PathVariable Long id, @RequestBody @Valid CursoRequest solicitud) {
        return ResponseEntity.ok(cursoService.updateCurso(id, solicitud));
    }

    @Operation(summary = "Eliminar un curso", description = "Elimina un curso del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Curso eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Curso no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}

