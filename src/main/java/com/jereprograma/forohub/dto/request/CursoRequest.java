package com.jereprograma.forohub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoRequest(
        @NotBlank String nombre,
        @NotBlank String categoria,
        @NotNull Boolean status
) {}
