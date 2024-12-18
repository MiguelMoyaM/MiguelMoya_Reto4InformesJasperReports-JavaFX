package com.example.miguelmoya_retoconjuntodiad_2parte.model;

import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.PeliculaDAO;
import jakarta.persistence.*;

@Entity
@Table(name = "copia")
public class Copia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pelicula", nullable = false)
    private Long idPelicula;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "estado")
    private String estado = "Nuevo";


    public Copia() {
    }


    public Copia(Long idPelicula, Long idUsuario, String estado) {
        this.idPelicula = idPelicula;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long idPelicula) {
        this.idPelicula = idPelicula;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Pelicula getPelicula() {
        PeliculaDAO peliculaDAO=new PeliculaDAO(HibernateUtil.getSessionFactory());
        return peliculaDAO.findById(idPelicula);
    }
}
