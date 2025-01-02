package com.jereprograma.forohub.controller;


import com.jereprograma.forohub.dto.request.AutenticacionRequest;
import com.jereprograma.forohub.infra.security.DatosJWTToken;
import com.jereprograma.forohub.infra.security.TokenService;
import com.jereprograma.forohub.model.Usuario;
import com.jereprograma.forohub.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    private final AuthenticationManager manager;

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    public AutenticacionController(AuthenticationManager manager, TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Operation(summary = "Autenticación de usuario", description = "Genera un token JWT para un usuario autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, token generado"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<DatosJWTToken> realizarLogin(@RequestBody @Valid AutenticacionRequest datos) {
        var authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
        var usuarioAutenticado = manager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

    @Operation(summary = "Refrescar token", description = "Genera un nuevo token JWT a partir de uno existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nuevo token generado con éxito"),
            @ApiResponse(responseCode = "401", description = "Token inválido o expirado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<DatosJWTToken> refreshToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var refreshToken = authHeader.replace("Bearer ", "");
        try {
            var email = tokenService.getSubject(refreshToken);
            var usuario = cargarUsuarioPorEmail(email);
            var newAccessToken = tokenService.generarToken(usuario);
            return ResponseEntity.ok(new DatosJWTToken(newAccessToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    private Usuario cargarUsuarioPorEmail(String email) {
        UserDetails userDetails = usuarioRepository.findByEmail(email);
        if (userDetails == null) {
            throw new RuntimeException("Usuario no encontrado con el correo electrónico: " + email);
        }
        return (Usuario) userDetails;
    }
}
