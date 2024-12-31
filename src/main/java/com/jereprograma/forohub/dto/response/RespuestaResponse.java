package com.jereprograma.forohub.dto.response;

import java.time.LocalDateTime;

public record RespuestaResponse(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean solucion,
        Boolean status,
        Long autorId,
        Long topicoId
) {}
