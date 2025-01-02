package com.jereprograma.forohub.validaciones.topicos;

import com.jereprograma.forohub.dto.request.TopicoRequest;
import com.jereprograma.forohub.repository.TopicoRepository;
import com.jereprograma.forohub.validaciones.Validador;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoDuplicado implements Validador<TopicoRequest> {

    private final TopicoRepository topicoRepository;

    public ValidadorTopicoDuplicado(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @Override
    public void validar(TopicoRequest datos) {
        if (topicoRepository.findByTituloAndMensaje(datos.titulo(), datos.mensaje()).isPresent()) {
            throw new RuntimeException("El tópico con este título y mensaje ya existe.");
        }
    }
}
