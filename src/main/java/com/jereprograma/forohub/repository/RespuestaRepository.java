package com.jereprograma.forohub.repository;

import com.jereprograma.forohub.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    // Métodos personalizados si son necesarios
}
