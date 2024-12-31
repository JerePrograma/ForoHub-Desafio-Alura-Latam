package com.jereprograma.forohub.dto.response;

import com.jereprograma.forohub.model.TopicoStatus;

import java.time.LocalDateTime;

public record TopicoResponse(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        TopicoStatus status, // Cambiado de Boolean a TopicoStatus
        Long autorId,
        Long cursoId,
        Long respuestaId
) {}

