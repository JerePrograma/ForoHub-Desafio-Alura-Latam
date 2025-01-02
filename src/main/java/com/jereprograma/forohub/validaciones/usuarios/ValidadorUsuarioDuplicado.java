package com.jereprograma.forohub.validaciones.usuarios;

import com.jereprograma.forohub.dto.request.UsuarioRequest;
import com.jereprograma.forohub.repository.UsuarioRepository;
import com.jereprograma.forohub.validaciones.Validador;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuarioDuplicado implements Validador<UsuarioRequest> {

    private final UsuarioRepository usuarioRepository;

    public ValidadorUsuarioDuplicado(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(UsuarioRequest datos) {
        if (usuarioRepository.existsByEmail(datos.email())) {
            throw new RuntimeException("El email ya está registrado");
        }
    }
}
