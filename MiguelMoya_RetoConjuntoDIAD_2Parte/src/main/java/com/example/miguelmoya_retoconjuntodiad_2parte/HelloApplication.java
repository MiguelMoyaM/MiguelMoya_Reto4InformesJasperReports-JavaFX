package com.example.miguelmoya_retoconjuntodiad_2parte;

import com.example.miguelmoya_retoconjuntodiad_2parte.dao.CopiaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.PeliculaDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.UsuarioDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Pelicula;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Usuario;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Usuario usuarioIniciado;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        UsuarioDAO usuarioDAO=new UsuarioDAO(HibernateUtil.getSessionFactory());
        PeliculaDAO peliculaDAO =new PeliculaDAO(HibernateUtil.getSessionFactory());
        CopiaDAO copiaDAO=new CopiaDAO(HibernateUtil.getSessionFactory());

        //Elimino todas las peliculas usuarios y copias para hacer pruebas durante el desarrollo de esta actividad
        usuarioDAO.deleteAll();
        peliculaDAO.deleteAllPeliculas();
        copiaDAO.deleteAllCopias();

        //Agrego algunas peliculas para que salgan por defecto
        Pelicula pelicula=new Pelicula("The Shawshank Redemption","Frank Darabont"," Un hombre es condenado injustamente a prisión y forja una amistad con un compañero de celda mientras busca una forma de escapar.",142 ,1994,"Drama","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMxNlztX1gXIJrNwXw77JF3xK1jWmawONGmQ&s");
        peliculaDAO.save(pelicula);
        pelicula=new Pelicula("Inception","Christopher Nolan"," Un ladrón experto en robar secretos a través de los sueños es contratado para realizar un trabajo aparentemente imposible: implantar una idea en la mente de una persona.",148  ,2010,"Ciencia ficción","https://m.media-amazon.com/images/I/912AErFSBHL._AC_UF894,1000_QL80_.jpg");
        peliculaDAO.save(pelicula);

        launch();
    }

    /***
     *
     * @param fxml = nombre del archivo fxml que se abrira como nueva ventana
     * @param titulo = nombre que aparecera en la parte superior de la nueva ventana
     * @param ventanaActual = referencia a la ventana actual para cerrarla, en caso de no querer cerrarla se debe marcar como null
     * @param ancho = Ancho que tendra la nueva ventana
     * @param altura = Altura que tendra la nueva ventana
     * @throws IOException
     */
    public static void abrirVentana(String fxml, String titulo, Stage ventanaActual, float ancho, float altura) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), ancho, altura);

        Stage stage= new Stage();
        stage.setScene(scene);
        stage.setTitle(titulo);
        stage.show();
        System.out.println("La ventana "+ fxml +" se ha abierto correctamente");

        if (ventanaActual!=null){
            ventanaActual.close();
            System.out.println("Se ha cerrado la ventana actual");
        }

    }
}