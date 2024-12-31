package com.jereprograma.forohub.dto.response;

public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        Long perfilId,
        Boolean status
) {}
