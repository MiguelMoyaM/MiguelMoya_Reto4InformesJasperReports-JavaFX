package com.example.miguelmoya_retoconjuntodiad_2parte.controller;

import com.example.miguelmoya_retoconjuntodiad_2parte.HelloApplication;
import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.CopiaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.PeliculaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Copia;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Pelicula;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class DetallePeliculaController {
    @FXML
    private ChoiceBox estadoChoiceBox;
    @FXML
    private ImageView portadaImage;
    @FXML
    private Label tituloLabel;
    @FXML
    private Label descripcionLabel;
    @FXML
    private Label directorLabel;
    @FXML
    private Label añoLabel;
    @FXML
    private Label generoLabel;
    @FXML
    private Label duracionLabel;
    PeliculaDAO peliculaDAO=new PeliculaDAO(HibernateUtil.getSessionFactory());


    public static Pelicula pelicula;

    public void initialize(){
        estadoChoiceBox.setValue("Nuevo");

        tituloLabel.setText(pelicula.getTitulo());
        portadaImage.setImage(new Image(pelicula.getImagen()));
        descripcionLabel.setText(pelicula.getDescripcion());
        directorLabel.setText("Director: "+pelicula.getDirector());
        añoLabel.setText("Año: "+(pelicula.getAnio()));
        generoLabel.setText("Genero: "+pelicula.getGenero());
        duracionLabel.setText("Duracion:"+(pelicula.getDuracion()));
        if (Integer.parseInt(String.valueOf(pelicula.getDuracion()))>210 ||  Integer.parseInt(String.valueOf(pelicula.getDuracion()))<30){
            //La pelicula tiene una duracion inapropiada y la marcamos en rojo
            duracionLabel.setStyle("-fx-background-color: #ae0b0b;");
        }else if (Integer.parseInt(String.valueOf(pelicula.getDuracion()))>30 ||  Integer.parseInt(String.valueOf(pelicula.getDuracion()))<60){
            duracionLabel.setStyle("-fx-background-color: #a3ae0b;");
        }else {
            duracionLabel.setStyle("-fx-background-color: #0bae13;");
        }

    }

    public void volverOnActionButton() throws IOException {
        pelicula=null;
        Stage ventanaActual=(Stage)tituloLabel.getScene().getWindow();
        HelloApplication.abrirVentana("AgregarCopia.fxml","Agregar Copia", ventanaActual,600,400);
    }

    public void agregarOnActionButton() {

        Copia copia=new Copia(pelicula.getId(), HelloApplication.usuarioIniciado.getId(), (String) estadoChoiceBox.getValue());

        CopiaDAO copiaDAO=new CopiaDAO(HibernateUtil.getSessionFactory());
        copiaDAO.save(copia);
    }
}
