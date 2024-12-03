package com.alura.literalura.repository;

import com.alura.literalura.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    @Query("SELECT a FROM Autor a WHERE UPPER(a.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))")
    List<Autor> findByNombreContainsIgnoreCase(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento >= :anioInicio AND (a.anioFallecimiento IS NULL OR a.anioFallecimiento <= :anioFin)")
    List<Autor> findAutoresByRangoDeAnios(@Param("anioInicio") int anioInicio, @Param("anioFin") int anioFin);

    @Query("SELECT a FROM Autor a JOIN FETCH a.libros WHERE UPPER(a.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))")
    List<Autor> findAutoresWithBooksByNombreContainsIgnoreCase(@Param("nombre") String nombre);
}
