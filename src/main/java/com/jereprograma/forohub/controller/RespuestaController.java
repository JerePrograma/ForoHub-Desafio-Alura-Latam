package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.RespuestaRequest;
import com.jereprograma.forohub.dto.response.RespuestaResponse;
import com.jereprograma.forohub.service.RespuestaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respuestas")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @Operation(summary = "Crear una respuesta", description = "Registra una nueva respuesta asociada a un tópico.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Respuesta creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<RespuestaResponse> crearRespuesta(@Valid @RequestBody RespuestaRequest request) {
        RespuestaResponse respuesta = respuestaService.crearRespuesta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @Operation(summary = "Listar respuestas", description = "Devuelve una lista de todas las respuestas registradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de respuestas obtenida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<RespuestaResponse>> listarRespuestas() {
        List<RespuestaResponse> respuestas = respuestaService.listarRespuestas();
        return ResponseEntity.ok(respuestas);
    }

    @Operation(summary = "Obtener una respuesta", description = "Devuelve los detalles de una respuesta específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta obtenida"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaResponse> obtenerRespuesta(@PathVariable Long id) {
        RespuestaResponse respuesta = respuestaService.obtenerRespuesta(id);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Actualizar una respuesta", description = "Modifica los datos de una respuesta existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta actualizada"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaResponse> actualizarRespuesta(
            @PathVariable Long id, @Valid @RequestBody RespuestaRequest request) {
        RespuestaResponse respuesta = respuestaService.actualizarRespuesta(id, request);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(summary = "Eliminar una respuesta", description = "Elimina una respuesta del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Respuesta eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Respuesta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRespuesta(@PathVariable Long id) {
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}