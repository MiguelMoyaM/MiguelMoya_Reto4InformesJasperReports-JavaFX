package com.example.miguelmoya_retoconjuntodiad_2parte.controller;

import com.example.miguelmoya_retoconjuntodiad_2parte.HelloApplication;
import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.UsuarioDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button accederButton;


    @FXML
    private TextArea nombreLoginTextArea;

    @FXML
    private PasswordField contraseñaLoginPasswordFIeld;

    @FXML
    private  Button sinCuentaButton;


    public LoginController() {
    }

    public void onSinCuentaButtonOnAction() throws IOException {
        System.out.println("El usuario a indicado que no tiene una cuenta");
        System.out.println("Accediendo a la pantalla de crear cuenta...");

        Stage ventanaActual=(Stage)accederButton.getScene().getWindow();
        HelloApplication.abrirVentana("CreateAccount.fxml","Create Account", ventanaActual,600,400);
    }

    public void onLoginButtonOnAction() throws IOException {

            System.out.println("Comprobando si exixte el usuario '"+nombreLoginTextArea.getText()+"'...");
            UsuarioDAO usuarioDAO = new UsuarioDAO(HibernateUtil.getSessionFactory());

            String nombre = nombreLoginTextArea.getText();
            String pass = contraseñaLoginPasswordFIeld.getText();

            Usuario usuario = usuarioDAO.findByName(nombre);
            if (usuario != null) {
                System.out.println("Usuario encontrado");
                System.out.println("Verificando contraseña...");

                if (pass.equals(usuario.getContraseña())) {
                    System.out.println("Usuario verificado con exito");
                    System.out.println("Iniciando sesion...");
                    HelloApplication.usuarioIniciado=usuario;
                    Stage ventanaActual=(Stage)accederButton.getScene().getWindow();
                    HelloApplication.abrirVentana("ListaPeliculas.fxml","Lista de Peliculas",ventanaActual,900,600);
                }else {
                    System.out.println("La contraseña no es correcta");
                }

            }else {
                System.out.println("El usuario no exixte");
            }




    }
}
