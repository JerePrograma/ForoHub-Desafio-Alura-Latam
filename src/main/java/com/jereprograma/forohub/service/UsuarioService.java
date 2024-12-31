package com.jereprograma.forohub.service;

import com.jereprograma.forohub.dto.request.UsuarioRequest;
import com.jereprograma.forohub.dto.response.UsuarioResponse;
import com.jereprograma.forohub.model.Usuario;
import com.jereprograma.forohub.repository.UsuarioRepository;
import com.jereprograma.forohub.model.Perfil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPassword(request.password());
        usuario.setPerfil(new Perfil(request.perfilId())); // Simula que `Perfil` tiene un constructor con ID.
        usuario.setStatus(request.status());

        usuario = usuarioRepository.save(usuario);

        return mapearAResponse(usuario);
    }

    public UsuarioResponse obtenerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return mapearAResponse(usuario);
    }

    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPassword(request.password());
        usuario.setPerfil(new Perfil(request.perfilId())); // Simula que `Perfil` tiene un constructor con ID.
        usuario.setStatus(request.status());

        usuario = usuarioRepository.save(usuario);

        return mapearAResponse(usuario);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponse mapearAResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getPerfil().getId(),
                usuario.getStatus()
        );
    }
}
