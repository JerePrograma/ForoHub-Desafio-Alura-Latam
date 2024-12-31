package com.jereprograma.forohub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PerfilRequest(
        @NotBlank String nombre,
        @NotNull Boolean activo
) {}
