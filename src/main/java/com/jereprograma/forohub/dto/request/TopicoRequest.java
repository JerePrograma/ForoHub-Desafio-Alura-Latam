package com.jereprograma.forohub.dto.request;

import com.jereprograma.forohub.model.TopicoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record TopicoRequest(
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotNull Long autorId,
        @NotNull Long cursoId,
        Optional<TopicoStatus> status // Sigue siendo opcional
) {}
