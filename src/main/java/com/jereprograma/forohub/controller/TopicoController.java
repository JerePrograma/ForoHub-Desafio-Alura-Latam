package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.TopicoRequest;
import com.jereprograma.forohub.dto.response.TopicoResponse;
import com.jereprograma.forohub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topicos")
public class TopicoController {

    private final TopicoService servicio;

    public TopicoController(TopicoService servicio) {
        this.servicio = servicio;
    }

    @Operation(summary = "Obtener un tópico por ID", description = "Devuelve los detalles de un tópico específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tópico encontrado"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TopicoResponse> obtenerTopicoPorId(@PathVariable Long id) {
        TopicoResponse respuesta = servicio.getTopicoById(id);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Crear un nuevo tópico", description = "Registra un nuevo tópico en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tópico creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<TopicoResponse> crearTopico(@RequestBody @Valid TopicoRequest solicitud) {
        TopicoResponse respuesta = servicio.createTopico(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Actualizar un tópico", description = "Modifica los datos de un tópico existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tópico actualizado"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TopicoResponse> actualizarTopico(
            @PathVariable Long id,
            @RequestBody @Valid TopicoRequest solicitud) {
        TopicoResponse respuesta = servicio.updateTopico(id, solicitud);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar un tópico por ID", description = "Elimina un tópico del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tópico eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Tópico no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        servicio.deleteTopico(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar tópicos", description = "Devuelve una lista de tópicos con soporte de paginación y ordenación.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tópicos obtenida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<TopicoResponse>> listarTopicos(Pageable pageable) {
        Page<TopicoResponse> respuesta = servicio.getAllTopicos(pageable);
        return ResponseEntity.ok(respuesta.getContent());
    }

    @Operation(summary = "Filtrar tópicos", description = "Devuelve una lista de tópicos filtrados por curso o año.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tópicos filtrados obtenidos"),
            @ApiResponse(responseCode = "404", description = "No se encontraron tópicos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/filtrar")
    public ResponseEntity<List<TopicoResponse>> filtrarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio) {
        List<TopicoResponse> respuesta = servicio.filtrarTopicos(curso, anio);
        return ResponseEntity.ok(respuesta);
    }
}
