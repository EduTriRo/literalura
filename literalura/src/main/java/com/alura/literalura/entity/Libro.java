package com.alura.literalura.entity;

import jakarta.persistence.*;
import java.time.Year;
import java.util.Objects;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private Year anioPublicacion;
    private Integer cantidadDescargas;
    private String idioma;
    private String tipoMedia;
    private String sinopsis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo != null ? titulo : "Título desconocido";
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Year getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Year anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public Integer getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public String getIdioma() {
        return idioma != null ? idioma : "Idioma desconocido";
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTipoMedia() {
        return tipoMedia != null ? tipoMedia : "Tipo de media desconocido";
    }

    public void setTipoMedia(String tipoMedia) {
        this.tipoMedia = tipoMedia;
    }

    public String getSinopsis() {
        return sinopsis != null ? sinopsis : "Sinopsis no disponible";
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getNombreAutor() {
        return autor != null ? autor.getNombre() : "Autor desconocido";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return titulo.equals(libro.titulo) &&
                idioma.equals(libro.idioma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, idioma);
    }

}
