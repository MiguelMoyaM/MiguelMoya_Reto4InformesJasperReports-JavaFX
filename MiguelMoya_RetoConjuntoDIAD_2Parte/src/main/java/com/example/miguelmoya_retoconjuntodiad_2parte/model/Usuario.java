package com.example.miguelmoya_retoconjuntodiad_2parte.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "contraseña", nullable = false)
    private String contraseña;

    @Column(name = "isAdmin")
    private Boolean isAdmin;

    public Usuario() {
    }

    public Usuario(String nombre, String contraseña) {
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public Usuario(String nombre, String contraseña, Boolean isAdmin) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
