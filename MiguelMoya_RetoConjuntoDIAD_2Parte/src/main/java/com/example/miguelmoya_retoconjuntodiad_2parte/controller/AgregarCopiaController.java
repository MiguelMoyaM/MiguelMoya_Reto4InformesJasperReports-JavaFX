package com.example.miguelmoya_retoconjuntodiad_2parte.controller;

import com.example.miguelmoya_retoconjuntodiad_2parte.HelloApplication;
import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.PeliculaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Pelicula;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.HibernateException;


public class AgregarCopiaController {

    @FXML
    private TableView<Pelicula> peliculaTableView;
    @FXML
    private TableColumn<Pelicula, String> tituloTableColumn;
    @FXML
    private TableColumn<Pelicula, String> directorTableColumn;
    @FXML
    private TableColumn<Pelicula, String> duracionTableColumn;
    @FXML
    private TableColumn<Pelicula, Integer> añoTableColumn;
    @FXML
    private TableColumn<Pelicula, String> generoTableColumn;
    @FXML
    private TableColumn<Pelicula, Void> detallesTableColumn;




    @FXML
    public void initialize() {
        PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
        List<Pelicula> listaPeliculas = peliculaDAO.findAll();
        ObservableList<Pelicula> peliculas = FXCollections.observableArrayList(listaPeliculas);

        peliculaTableView.setItems(peliculas);

        tituloTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));
        directorTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDirector()));
        duracionTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDuracion().toString()));
        añoTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getAnio()));
        generoTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getGenero()));

        detallesTableColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Pelicula, Void> call(TableColumn<Pelicula, Void> param) {
                return new TableCell<>() {
                    private final Button btnDetalles = new Button("+");

                    {
                        btnDetalles.setOnAction(event -> {
                            Pelicula pelicula = getTableView().getItems().get(getIndex());
                            DetallePeliculaController.pelicula=pelicula;
                            try {
                                Stage ventanaActual = (Stage) peliculaTableView.getScene().getWindow();
                                HelloApplication.abrirVentana("DetallePelicula.fxml","Detalles de "+pelicula.getTitulo(),ventanaActual,1920,1080);
                            } catch (IOException e) {
                                System.out.println("No se ha podido abrir la ventana de detalles de la pelicula:");
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btnDetalles);
                        }
                    }
                };
            }
        });
    }

    public void volverOnActionButton() throws IOException {
        Stage ventanaActual = (Stage) peliculaTableView.getScene().getWindow();
        HelloApplication.abrirVentana("ListaPeliculas.fxml","Lista de Peliculas",ventanaActual,900,600);
    }

    /***
     * Muestra una ventana en la que se puede ver informacion de peliculas filtradas por lo que indique el Combobox
     * @param actionEvent
     */
    public void mostrarListadoPeliculasOnActionButton(ActionEvent actionEvent) {
        var params = new HashMap<String,Object>(0);
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retoConjunto","root","10072016K@");

            JasperPrint jp = JasperFillManager.fillReport(("Reto4v2.jasper"), params, connection);


            JasperViewer.viewReport(jp, false);

            System.out.println("Reporte de listado de películas de BDD exportado con éxito");
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la conexión de Hibernate", e);
        }
    }



}
