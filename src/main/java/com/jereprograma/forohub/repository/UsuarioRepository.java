package com.jereprograma.forohub.repository;

import com.jereprograma.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar un usuario por correo electrónico (opcional, útil para autenticación)
    UserDetails findByEmail(String Email);

    // Verificar si existe un usuario por correo electrónico
    boolean existsByEmail(String Email);
}
