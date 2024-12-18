package com.example.miguelmoya_retoconjuntodiad_2parte.controller;

import com.example.miguelmoya_retoconjuntodiad_2parte.HelloApplication;
import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.PeliculaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Pelicula;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AgregarPeliculaController {

    @FXML
    private TextField urlImagenTextField;
    @FXML
    private TextField tituloTextField;
    @FXML
    private TextField dicrectorTextField;
    @FXML
    private TextField descripcionTextField;
    @FXML
    private TextField duracionTextField;
    @FXML
    private TextField añoTextField;
    @FXML
    private TextField generoTextField;



    public void cancelarOnActionButton() throws IOException {
        Stage ventanaActual = (Stage) tituloTextField.getScene().getWindow();
        HelloApplication.abrirVentana("ListaPeliculas.fxml","Lista de Peliculas",ventanaActual,900,600);
    }

    public void agregarOnActionButton() {
        Pelicula pelicula=new Pelicula(tituloTextField.getText(),dicrectorTextField.getText(),descripcionTextField.getText(),Integer.parseInt(duracionTextField.getText()),Integer.parseInt(añoTextField.getText()),generoTextField.getText(),urlImagenTextField.getText());
        PeliculaDAO peliculaDAO=new PeliculaDAO(HibernateUtil.getSessionFactory());
        peliculaDAO.save(pelicula);
    }
}
