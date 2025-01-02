package com.jereprograma.forohub.service;

import com.jereprograma.forohub.dto.request.TopicoRequest;
import com.jereprograma.forohub.dto.response.TopicoResponse;
import com.jereprograma.forohub.model.Topico;
import com.jereprograma.forohub.model.TopicoStatus;
import com.jereprograma.forohub.model.Usuario;
import com.jereprograma.forohub.model.Curso;
import com.jereprograma.forohub.repository.TopicoRepository;
import com.jereprograma.forohub.repository.UsuarioRepository;
import com.jereprograma.forohub.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;

    public TopicoService(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

    /**
     * Lista todos los tópicos con paginación.
     * @param pageable Información de paginación y ordenación.
     * @return Página de tópicos mapeados a DTOs de respuesta.
     */
    public Page<TopicoResponse> getAllTopicos(Pageable pageable) {
        // Lista de campos válidos para ordenar
        List<String> camposPermitidos = List.of("titulo", "mensaje", "fechaCreacion", "status");

        pageable.getSort().forEach(order -> {
            String propiedad = order.getProperty();
            if (!camposPermitidos.contains(propiedad)) {
                throw new IllegalArgumentException("El campo '" + propiedad + "' no es válido para la ordenación. Campos permitidos: " + camposPermitidos);
            }
        });

        return topicoRepository.findAll(pageable).map(this::mapToResponse);
    }


    /**
     * Obtiene un tópico por ID y lo mapea a un DTO de respuesta.
     */
    public TopicoResponse getTopicoById(Long id) {
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado con ID: " + id));
        return mapToResponse(topico);
    }

    /**
     * Crea un nuevo tópico.
     *
     * @param request Datos del tópico.
     * @return Tópico creado mapeado a DTO de respuesta.
     */
    public TopicoResponse createTopico(TopicoRequest request) {
        // Validar si existe un tópico duplicado.
        Optional<Topico> existingTopico = topicoRepository.findByTituloAndMensaje(request.titulo(), request.mensaje());
        if (existingTopico.isPresent()) {
            throw new RuntimeException("El tópico con el mismo título y mensaje ya existe.");
        }

        // Mapear el DTO a la entidad y guardar.
        Topico topico = mapToEntity(request);
        Topico savedTopico = topicoRepository.save(topico);

        return mapToResponse(savedTopico);
    }

    /**
     * Actualiza un tópico existente con los datos de un DTO de solicitud.
     * @param id ID del tópico a actualizar.
     * @param request DTO de solicitud con los datos a actualizar.
     * @return DTO de respuesta con los datos actualizados del tópico.
     */
    public TopicoResponse updateTopico(Long id, TopicoRequest request) {
        // Verifica si el tópico existe
        Topico existingTopico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado con ID: " + id));

        // Actualiza los campos requeridos
        existingTopico.setTitulo(request.titulo());
        existingTopico.setMensaje(request.mensaje());
        existingTopico.setAutor(usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.autorId())));
        existingTopico.setCurso(cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + request.cursoId())));

        // Actualiza el estado si está presente en la solicitud
        request.status().ifPresent(existingTopico::setStatus);

        // Guarda los cambios
        Topico updatedTopico = topicoRepository.save(existingTopico);

        return mapToResponse(updatedTopico);
    }

    /**
     * Elimina un tópico por su ID.
     */
    public void deleteTopico(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new RuntimeException("Tópico no encontrado con ID: " + id);
        }
        topicoRepository.deleteById(id);
    }

    /**
     * Mapea un DTO de solicitud a una entidad `Topico`.
     */
    private Topico mapToEntity(TopicoRequest request) {
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.autorId()));

        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + request.cursoId()));

        Topico topico = new Topico();
        topico.setTitulo(request.titulo());
        topico.setMensaje(request.mensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);
        topico.setFechaCreacion(LocalDateTime.now());

        // Agrega esta línea para configurar el valor del campo status
        request.status().ifPresentOrElse(
                topico::setStatus,
                () -> topico.setStatus(TopicoStatus.ABIERTO) // Valor predeterminado
        );

        return topico;
    }


    /**
     * Mapea una entidad `Topico` a un DTO de respuesta.
     */
    private TopicoResponse mapToResponse(Topico topico) {
        if (topico == null) {
            throw new RuntimeException("El tópico no puede ser nulo.");
        }
        return new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getId() : null,
                topico.getCurso() != null ? topico.getCurso().getId() : null,
                topico.getRespuesta() != null ? topico.getRespuesta().getId() : null
        );
    }


    public List<TopicoResponse> filtrarTopicos(String curso, Integer anio) {
        List<Topico> topicos;

        if (curso != null && anio != null) {
            topicos = topicoRepository.findByCursoAndYear(curso, anio);
        } else if (curso != null) {
            topicos = topicoRepository.findAll().stream()
                    .filter(t -> t.getCurso() != null && curso.equalsIgnoreCase(t.getCurso().getNombre()))
                    .collect(Collectors.toList());
        } else if (anio != null) {
            topicos = topicoRepository.findAll().stream()
                    .filter(t -> t.getFechaCreacion() != null && t.getFechaCreacion().getYear() == anio)
                    .collect(Collectors.toList());
        } else {
            topicos = topicoRepository.findAll();
        }

        if (topicos.isEmpty()) {
            throw new RuntimeException("No se encontraron tópicos con los filtros especificados.");
        }

        return topicos.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


}
