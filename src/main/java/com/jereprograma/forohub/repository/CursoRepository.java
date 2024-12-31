package com.jereprograma.forohub.repository;

import com.jereprograma.forohub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Buscar cursos por categoría
    List<Curso> findByCategoria(String categoria);

    // Verificar si un curso existe por nombre
    boolean existsByNombre(String nombre);
}
