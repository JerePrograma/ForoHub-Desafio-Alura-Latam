package com.jereprograma.forohub.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 300)
    private String password;

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @Column(nullable = false)
    private Boolean status;

    // Métodos de UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + perfil.getNombre().toUpperCase()); // Asegúrate de usar el prefijo ROLE_
    }

    @Override
    public String getUsername() {
        return email; // Spring Security utiliza esto como el nombre de usuario
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Define la lógica según las reglas de tu negocio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Define la lógica según las reglas de tu negocio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Define la lógica según las reglas de tu negocio
    }

    @Override
    public boolean isEnabled() {
        return status; // Retorna el estado del usuario
    }
}
