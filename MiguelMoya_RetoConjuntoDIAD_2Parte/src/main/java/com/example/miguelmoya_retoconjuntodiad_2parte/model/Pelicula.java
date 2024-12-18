package com.example.miguelmoya_retoconjuntodiad_2parte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "director")
    private String director;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "anio")
    private Integer anio;

    @Column(name = "genero")
    private String genero;
    @Column(name = "imagen")
    private String imagen;


    public Pelicula() {
    }

    public Pelicula(String titulo, String director, String descripcion, Integer duracion, Integer anio, String genero, String imagen) {
        this.titulo = titulo;
        this.director = director;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.anio = anio;
        this.genero = genero;
        this.imagen = imagen;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
