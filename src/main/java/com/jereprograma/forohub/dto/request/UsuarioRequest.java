package com.jereprograma.forohub.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest(
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String password,
        Long perfilId, // Ahora es opcional
        Boolean status
) {
}