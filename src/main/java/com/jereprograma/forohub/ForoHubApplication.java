package com.jereprograma.forohub;

import com.jereprograma.forohub.model.Perfil;
import com.jereprograma.forohub.model.Usuario;
import com.jereprograma.forohub.repository.PerfilRepository;
import com.jereprograma.forohub.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.*;

@SpringBootApplication
public class ForoHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForoHubApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(PerfilRepository perfilRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Inicializar perfiles si no existen
            if (perfilRepository.count() == 0) {
                Perfil adminPerfil = perfilRepository.save(new Perfil(null, "Admin", true));
                perfilRepository.save(new Perfil(null, "Usuario", true));
                perfilRepository.save(new Perfil(null, "Moderador", true));

                // Crear un usuario superadmin si no existe
                if (!usuarioRepository.existsByEmail("superadmin@mail.com")) {
                    Usuario superadmin = new Usuario();
                    superadmin.setNombre("Super Admin");
                    superadmin.setEmail("superadmin@mail.com");
                    superadmin.setPassword(passwordEncoder.encode("superpassword")); // Encripta la contraseña
                    superadmin.setPerfil(adminPerfil); // Asigna el perfil de Admin
                    superadmin.setStatus(true);
                    usuarioRepository.save(superadmin);
                }
            }
        };
    }


}
