package com.jereprograma.forohub.repository;

import com.jereprograma.forohub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Validar si ya existe un tópico con el mismo título y mensaje.
    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t WHERE t.curso.nombre = :nombreCurso AND YEAR(t.fechaCreacion) = :year")
    List<Topico> findByCursoAndYear(@Param("nombreCurso") String nombreCurso, @Param("year") int year);

}
