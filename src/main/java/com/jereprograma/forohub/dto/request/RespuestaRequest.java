package com.jereprograma.forohub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespuestaRequest(
        @NotBlank String mensaje,
        @NotNull Boolean solucion,
        @NotNull Boolean status,
        @NotNull Long autorId,
        @NotNull Long topicoId
) {}
