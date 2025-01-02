package com.jereprograma.forohub.service;

import com.jereprograma.forohub.dto.request.PerfilRequest;
import com.jereprograma.forohub.dto.response.PerfilResponse;
import com.jereprograma.forohub.model.Perfil;
import com.jereprograma.forohub.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Crea un nuevo perfil a partir de un PerfilRequest.
     *
     * @param request Datos para crear el perfil.
     * @return PerfilResponse con los datos del perfil creado.
     */
    public PerfilResponse crearPerfil(PerfilRequest request) {
        Perfil perfil = new Perfil();
        perfil.setNombre(request.nombre());
        perfil.setActivo(request.activo());

        Perfil perfilGuardado = perfilRepository.save(perfil);
        return mapToResponse(perfilGuardado);
    }

    /**
     * Obtiene todos los perfiles y los convierte en PerfilResponse.
     *
     * @return Lista de PerfilResponse.
     */
    public List<PerfilResponse> obtenerPerfiles() {
        return perfilRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un perfil por ID y lo convierte en PerfilResponse.
     *
     * @param id ID del perfil.
     * @return PerfilResponse con los datos del perfil.
     */
    public PerfilResponse obtenerPerfilPorId(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + id));
        return mapToResponse(perfil);
    }

    /**
     * Actualiza un perfil existente con los datos de un PerfilRequest.
     *
     * @param id ID del perfil a actualizar.
     * @param request Datos del perfil a actualizar.
     * @return PerfilResponse con los datos actualizados.
     */
    public PerfilResponse actualizarPerfil(Long id, PerfilRequest request) {
        Perfil perfilExistente = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + id));

        perfilExistente.setNombre(request.nombre());
        perfilExistente.setActivo(request.activo());

        Perfil perfilActualizado = perfilRepository.save(perfilExistente);
        return mapToResponse(perfilActualizado);
    }

    /**
     * Elimina un perfil por su ID.
     *
     * @param id ID del perfil a eliminar.
     */
    public void eliminarPerfil(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new RuntimeException("Perfil no encontrado con ID: " + id);
        }
        perfilRepository.deleteById(id);
    }

    /**
     * Convierte un Perfil en un PerfilResponse.
     *
     * @param perfil Entidad Perfil.
     * @return DTO PerfilResponse.
     */
    private PerfilResponse mapToResponse(Perfil perfil) {
        return new PerfilResponse(
                perfil.getId(),
                perfil.getNombre(),
                perfil.getActivo()
        );
    }
}
