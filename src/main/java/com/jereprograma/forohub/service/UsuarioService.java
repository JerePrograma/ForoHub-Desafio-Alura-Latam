package com.jereprograma.forohub.service;

import com.jereprograma.forohub.dto.request.UsuarioRequest;
import com.jereprograma.forohub.dto.response.UsuarioResponse;
import com.jereprograma.forohub.model.Usuario;
import com.jereprograma.forohub.repository.PerfilRepository;
import com.jereprograma.forohub.repository.UsuarioRepository;
import com.jereprograma.forohub.validaciones.Validador;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final List<Validador<UsuarioRequest>> validadores;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, List<Validador<UsuarioRequest>> validadores, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.validadores = validadores;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        // Ejecutar validaciones
        validadores.forEach(validador -> validador.validar(request));

        // Asignar el perfil por defecto si no está especificado
        var perfilId = request.perfilId() != null ? request.perfilId() : 2L; // Por defecto ID = 2
        var perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + perfilId));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setPerfil(perfil);
        usuario.setStatus(request.status() != null ? request.status() : true); // Status por defecto: true

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return mapearAResponse(usuarioGuardado);
    }

    public UsuarioResponse actualizarUsuario(Long id, UsuarioRequest request) {
        // Verificar existencia del usuario
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Ejecutar validadores
        validadores.forEach(validador -> validador.validar(request));

        // Buscar perfil asociado
        var perfil = perfilRepository.findById(request.perfilId())
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + request.perfilId()));

        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setPerfil(perfil);
        usuario.setStatus(request.status());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return mapearAResponse(usuarioActualizado);
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
