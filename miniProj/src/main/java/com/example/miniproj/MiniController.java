package com.example.miniproj;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MiniController implements Initializable {
    @FXML
    private TextField nom;
    @FXML
    private PasswordField mdpp;
    @FXML
    private Button connexion;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/hopitale?user=system&password=StrongPassword1!";

    @FXML
    private void handleConnexionButton(ActionEvent event) {
        String username = nom.getText();
        String password = mdpp.getText();

        // Fetch user data from the database
        if (fetchUserData(username, password)) {
            // Redirection to the min-view.fxml page
            redirectToMinView(event);
        } else {
            showAlert("Erreur", "Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private boolean fetchUserData(String username, String password) {
        String query = "SELECT * FROM personnel WHERE login = ? AND password = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Check if result set is not empty
                return resultSet.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void redirectToMinView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproj/min-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Hospital Management");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Une erreur s'est produite lors du chargement de la vue.");
            e.printStackTrace(); // Or log the exception
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connexion.setOnAction(this::handleConnexionButton);
    }
}
