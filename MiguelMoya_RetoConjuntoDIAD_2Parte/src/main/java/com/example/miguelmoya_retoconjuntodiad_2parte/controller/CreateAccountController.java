package com.example.miguelmoya_retoconjuntodiad_2parte.controller;

import com.example.miguelmoya_retoconjuntodiad_2parte.HelloApplication;
import com.example.miguelmoya_retoconjuntodiad_2parte.HibernateUtil;
import com.example.miguelmoya_retoconjuntodiad_2parte.dao.UsuarioDAO;
import com.example.miguelmoya_retoconjuntodiad_2parte.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    private Button conCuentaButton;
    @FXML
    private TextArea nombreCreateAccountTextArea;
    @FXML
    private PasswordField contraseñaCreateAccountPasswordField;

    @FXML
    private CheckBox adminCheckBox;


    // Constructor por defecto
    public CreateAccountController() {
    }


    @FXML
    public void onConCuentaButtonOnAction() throws IOException {
        System.out.println("El usuario ha indicado que ya tiene una cuenta");
        System.out.println("Accediendo a la pantalla de inicio de sesión...");

        Stage ventanaActual = (Stage) conCuentaButton.getScene().getWindow();
        HelloApplication.abrirVentana("Login.fxml", "Login", ventanaActual,600,400);
    }

    @FXML
    public void onRegistrarButtonAction() throws IOException {
        System.out.println("El usuario ha ordenado que se registre el usuario establecido");
        String nombre = nombreCreateAccountTextArea.getText();
        String contraseña = contraseñaCreateAccountPasswordField.getText();
        Boolean isAdmin= adminCheckBox.isSelected();
        Usuario usuario=new Usuario(nombre,contraseña,isAdmin);

        System.out.println("Comprobando si el usuario es válido...");
        UsuarioDAO usuarioDAO=new UsuarioDAO(HibernateUtil.getSessionFactory());
        if (usuarioDAO.findByName(nombre)!=null){
            System.out.println("Ya exixte un usuario con ese nombre");
        }else {
            System.out.println("El usuario introducido es valido");
            System.out.println("Resgistrando usuario en la BDD...");
            usuarioDAO.save(usuario);
            System.out.println("Usuario registrado correctamente");

            if (usuarioDAO.findByName(nombre)!=null) {
                System.out.println("Usuario registrado correctamente");
                System.out.println("Dirigiendo a la pantalla de inicio de sesion...");
                onConCuentaButtonOnAction();
            }else {
                System.out.println("*Error, el usuario no se ha guardado correctamente en la BDD*");
            }
        }

    }
}
