package com.alura.literalura.repository;

import com.alura.literalura.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    boolean existsByTituloIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE UPPER(l.titulo) LIKE UPPER(CONCAT('%', :titulo, '%')) ORDER BY l.titulo ASC")
    List<Libro> findByTituloContainsIgnoreCase(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.anioPublicacion = :anio ORDER BY l.titulo ASC")
    List<Libro> findByAnioPublicacion(@Param("anio") Integer anio);

    @Modifying
    @Query("DELETE FROM Libro l WHERE l.id NOT IN (SELECT MIN(l2.id) FROM Libro l2 GROUP BY l2.titulo)")
    void eliminarDuplicados();
}
