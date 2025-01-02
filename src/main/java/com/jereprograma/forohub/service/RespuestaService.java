package com.jereprograma.forohub.service;

import com.jereprograma.forohub.dto.request.RespuestaRequest;
import com.jereprograma.forohub.dto.response.RespuestaResponse;
import com.jereprograma.forohub.model.Respuesta;
import com.jereprograma.forohub.model.Topico;
import com.jereprograma.forohub.model.Usuario;
import com.jereprograma.forohub.repository.RespuestaRepository;
import com.jereprograma.forohub.repository.TopicoRepository;
import com.jereprograma.forohub.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public RespuestaService(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RespuestaResponse crearRespuesta(RespuestaRequest request) {
        // Buscar al autor (Usuario) por ID
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.autorId()));

        // Buscar al tópico por ID
        Topico topico = topicoRepository.findById(request.topicoId())
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado con ID: " + request.topicoId()));

        // Crear la respuesta
        var respuesta = new Respuesta();
        respuesta.setMensaje(request.mensaje());
        respuesta.setFechaCreacion(LocalDateTime.now());
        respuesta.setAutor(autor); // Asignar el autor recuperado
        respuesta.setTopico(topico); // Asignar el tópico recuperado
        respuesta.setSolucion(false);
        respuesta.setStatus(request.status());

        respuestaRepository.save(respuesta);

        return mapearAResponse(respuesta);
    }


    public List<RespuestaResponse> listarRespuestas() {
        return respuestaRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public RespuestaResponse obtenerRespuesta(Long id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));
        return mapearAResponse(respuesta);
    }

    public RespuestaResponse actualizarRespuesta(Long id, RespuestaRequest request) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));

        respuesta.setMensaje(request.mensaje());
        respuesta.setStatus(request.status());

        respuestaRepository.save(respuesta);
        return mapearAResponse(respuesta);
    }

    public void eliminarRespuesta(Long id) {
        if (!respuestaRepository.existsById(id)) {
            throw new RuntimeException("Respuesta no encontrada con ID: " + id);
        }
        respuestaRepository.deleteById(id);
    }

    public void marcarComoSolucion(Long id) {
        var respuesta = respuestaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada con ID: " + id));

        respuesta.setSolucion(true);
        respuestaRepository.save(respuesta);
    }

    private RespuestaResponse mapearAResponse(Respuesta respuesta) {
        return new RespuestaResponse(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion(),
                respuesta.getStatus(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId()
        );
    }
}
