package com.jereprograma.forohub.controller;

import com.jereprograma.forohub.dto.request.UsuarioRequest;
import com.jereprograma.forohub.dto.response.UsuarioResponse;
import com.jereprograma.forohub.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Este endpoint permite registrar un nuevo usuario con los datos proporcionados.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse usuarioResponse = usuarioService.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }

    @Operation(summary = "Actualizar un usuario existente", description = "Permite modificar los datos de un usuario específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Usuario actualizado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse usuarioResponse = usuarioService.actualizarUsuario(id, request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioResponse);
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve los datos de un usuario en función de su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        UsuarioResponse usuarioResponse = usuarioService.obtenerUsuario(id);
        return ResponseEntity.ok(usuarioResponse);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista con todos los usuarios registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(summary = "Eliminar un usuario por ID", description = "Permite eliminar un usuario especificado por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado con éxito"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
