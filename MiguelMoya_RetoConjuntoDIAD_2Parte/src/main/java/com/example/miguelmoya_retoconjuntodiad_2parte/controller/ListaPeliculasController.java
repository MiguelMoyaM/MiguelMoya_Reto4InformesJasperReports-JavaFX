package com.example.miguelmoya_retoconjuntodiad_2parte.controller;

import com.example.miguelmoya_retoconjuntodiad_2parte.HelloApplication;
import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.CopiaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Copia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ListaPeliculasController {

    @FXML
    private Label mensajeBienvenidaLabel;

    @FXML
    private Button agregarNuevaPeliculaButton;

    @FXML
    private Button agregarNuevaCopiaButton;

    @FXML
    private TableView<Copia> peliculaTableView;

    @FXML
    private TableColumn<Copia, String> tituloTableColumn;
    @FXML
    private TableColumn<Copia, String> directorTableColumn;
    @FXML
    private TableColumn<Copia, String> duracionTableColumn;
    @FXML
    private TableColumn<Copia, Integer> añoTableColumn;
    @FXML
    private TableColumn<Copia, String> generoTableColumn;
    @FXML
    private TableColumn<Copia, String> estadoTableColumn;
    @FXML
    private TableColumn<Copia, Void> detallesTableColumn;

    @FXML
    private ComboBox<String> comboBox;

    public ListaPeliculasController() {
    }

    public void initialize() {
        iniciarSesion();

        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

        List<Copia> listaCopias = copiaDAO.findByUserId(HelloApplication.usuarioIniciado.getId());
        ObservableList<Copia> copias = FXCollections.observableArrayList(listaCopias);
        peliculaTableView.setItems(copias);

        tituloTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPelicula().getTitulo() != null ? cellData.getValue().getPelicula().getTitulo() : "Sin título"));

        directorTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPelicula().getDirector() != null ? cellData.getValue().getPelicula().getDirector() : "Sin director"));

        duracionTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPelicula().getDuracion() != null ? cellData.getValue().getPelicula().getDuracion().toString() : "0"));

        añoTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getPelicula().getAnio() != null ? cellData.getValue().getPelicula().getAnio() : 0));

        generoTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPelicula().getGenero() != null ? cellData.getValue().getPelicula().getGenero() : "Sin género"));

        estadoTableColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado() != null ? cellData.getValue().getEstado() : "Estado desconocido"));

        detallesTableColumn.setCellFactory(col -> new TableCell<>() {
            private final Button detallesButton = new Button("Imprimir");

            {
                detallesButton.setOnAction(event -> {
                    Copia copia = getTableView().getItems().get(getIndex());
                    ImprimirDetallesCopia(copia);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detallesButton);
                }
            }
        });
        comboBox.getItems().addAll("Peliculas en mal estado", "Peliculas con mas de una copia");
        if (!comboBox.getItems().isEmpty()) {
            comboBox.setValue(comboBox.getItems().get(0));
        }
    }

    public void iniciarSesion() {
        mensajeBienvenidaLabel.setText("Hola, " + HelloApplication.usuarioIniciado.getNombre());

        if (HelloApplication.usuarioIniciado.getAdmin()) {
            agregarNuevaPeliculaButton.setVisible(true);
        } else {
            agregarNuevaCopiaButton.setLayoutX(755f);
        }
    }

    public void cerrarSesionOnActionButton() throws IOException {
        System.out.println("Cerrando sesion...");
        HelloApplication.usuarioIniciado = null;
        Stage ventanaActual = (Stage) mensajeBienvenidaLabel.getScene().getWindow();
        HelloApplication.abrirVentana("Login.fxml", "Login", ventanaActual, 600, 400);
        System.out.println("Sesion cerrada con exito");
    }

    public void agregarNuevaPeliculaOnActionButton() throws IOException {
        Stage ventanaActual = (Stage) peliculaTableView.getScene().getWindow();
        HelloApplication.abrirVentana("AgregarPelicula.fxml", "Agregar Pelicula", ventanaActual, 600, 400);
    }

    public void agregarNuevaCopiaOnActionButton() throws IOException {
        Stage ventanaActual = (Stage) peliculaTableView.getScene().getWindow();
        HelloApplication.abrirVentana("AgregarCopia.fxml", "Agregar Copia", ventanaActual, 600, 400);
    }

    public void MostrarInformeOnActionButton(){
        String eleccion=comboBox.getValue();

        String nombre=HelloApplication.usuarioIniciado.getNombre();
        var params = new HashMap();
        params.put("nombre",nombre);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retoConjunto", "root", "10072016K@");
            JasperPrint  jp;
            if (!comboBox.getItems().isEmpty()) {

                if (eleccion.equals(comboBox.getItems().get(0))) {
                    System.out.println("Generando lista de peliculas estropeadas "+HelloApplication.usuarioIniciado.getNombre()+"...");
                    jp=JasperFillManager.fillReport(("PeliculasMalEstadov2.jasper"),params,connection);
                    JasperViewer.viewReport(jp, false);
                } else {
                    System.out.println("Generando lista de peliculas repetidas del usuario "+HelloApplication.usuarioIniciado.getNombre()+"...");
                    jp=JasperFillManager.fillReport(("PeliculasRepetidas.jasper"),params,connection);
                    JasperViewer.viewReport(jp, false);
                }
            }




            System.out.println("Reporte de listado de películas de BDD exportado con éxito");
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la conexión de Hibernate", e);
        }

    }

    /***
     * Exporta un PDF con la informacion de la copia de una pelicula en especifico
     * @param copia a mostrar en el PDF
     */
    private void ImprimirDetallesCopia(Copia copia) {
        Long idCopia=copia.getId();
        var params = new HashMap();
        params.put("idCopia",idCopia);
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/retoConjunto", "root", "10072016K@");
            JasperPrint jp = JasperFillManager.fillReport("MotrarDetallesCopia.jasper", params,connection );
            JasperExportManager.exportReportToPdfFile(jp,"DetallesCopia_"+copia.getId()+".pdf");
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
