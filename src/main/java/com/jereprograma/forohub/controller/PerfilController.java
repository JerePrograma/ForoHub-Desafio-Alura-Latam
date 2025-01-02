package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.PerfilRequest;
import com.jereprograma.forohub.dto.response.PerfilResponse;
import com.jereprograma.forohub.dto.response.RespuestaResponse;
import com.jereprograma.forohub.model.Perfil;
import com.jereprograma.forohub.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @Operation(summary = "Crear un perfil", description = "Registra un nuevo perfil en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Perfil creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<PerfilResponse> crearPerfil(@RequestBody @Valid PerfilRequest request) {
        PerfilResponse perfilResponse = perfilService.crearPerfil(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilResponse);
    }

    @Operation(summary = "Listar perfiles", description = "Devuelve una lista de todos los perfiles registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de perfiles obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<PerfilResponse>> listarPerfiles() {
        return ResponseEntity.ok(perfilService.obtenerPerfiles());
    }

    @Operation(summary = "Obtener un perfil", description = "Devuelve los detalles de un perfil específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil encontrado con éxito"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponse> obtenerPerfilPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.obtenerPerfilPorId(id));
    }

    @Operation(summary = "Actualizar un perfil", description = "Modifica los datos de un perfil existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponse> actualizarPerfil(
            @PathVariable Long id, @RequestBody @Valid PerfilRequest request) {
        return ResponseEntity.ok(perfilService.actualizarPerfil(id, request));
    }

    @Operation(summary = "Eliminar un perfil", description = "Elimina un perfil del sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Perfil eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        perfilService.eliminarPerfil(id);
        return ResponseEntity.noContent().build();
    }
}

