package com.alura.literalura.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuarioBusqueda;
    private LocalDateTime fechaConsulta;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "consulta_id") // Establece la relación unidireccional
    private List<Libro> librosResultados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuarioBusqueda() {
        return usuarioBusqueda;
    }

    public void setUsuarioBusqueda(String usuarioBusqueda) {
        this.usuarioBusqueda = usuarioBusqueda;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDateTime fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public List<Libro> getLibrosResultados() {
        return librosResultados;
    }

    public void setLibrosResultados(List<Libro> librosResultados) {
        this.librosResultados = librosResultados;
    }
}
