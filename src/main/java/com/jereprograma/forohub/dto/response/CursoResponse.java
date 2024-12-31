package com.jereprograma.forohub.dto.response;

public record CursoResponse(
        Long id,
        String nombre,
        String categoria,
        Boolean status
) {}
