package com.example.miniproj;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Medicament;
import model.listMedicament;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionMedicamentController implements Initializable {

    @FXML
    private TableView<Medicament> listmed;

    @FXML
    private TableColumn<Medicament, Integer> crefmed;

    @FXML
    private TableColumn<Medicament, String> clibmed;

    @FXML
    private TableColumn<Medicament, Double> cprixmed;

    @FXML
    private TextField refmed;

    @FXML
    private TextField libmed;

    @FXML
    private TextField prixmed;

    @FXML
    private Button enregp;

    @FXML
    private Button modifp;

    @FXML
    private Button suppp;

    @FXML
    private Button listPatient;

    @FXML
    private Button listPatient1;

    @FXML
    private Button searchButton;

    private Medicament selectedMedicament; // Keep track of the selected medicament for modification

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns with property values from Medicament class
        crefmed.setCellValueFactory(new PropertyValueFactory<>("ref"));
        clibmed.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        cprixmed.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Set button actions
        enregp.setOnAction(this::handleSaveButton);
        modifp.setOnAction(this::handleUpdateButton);
        suppp.setOnAction(this::handleDeleteButton);
        listPatient.setOnAction(this::openMminViewCopie);
        listPatient1.setOnAction(this::openMminViewLMP);

        loadMedicaments(); // Load medicaments from database when initializing the controller
    }

    private void loadMedicaments() {
        ObservableList<Medicament> medicament = listMedicament.getMedicament();
        if (medicament != null) {
            listmed.setItems(medicament);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load medicaments.");
        }
    }

    private void handleUpdateButton(ActionEvent event) {
        selectedMedicament = listmed.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            refmed.setText(String.valueOf(selectedMedicament.getRef()));
            libmed.setText(selectedMedicament.getLibelle());
            prixmed.setText(String.valueOf(selectedMedicament.getPrix()));
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a medicament to update.");
        }
    }

    private void handleSaveButton(ActionEvent event) {
        boolean isUpdate = selectedMedicament != null; // Check if an existing medicament is being updated
        int ref = Integer.parseInt(refmed.getText());
        String libelle = libmed.getText();
        double prix = Double.parseDouble(prixmed.getText());

        Medicament newMedicament = new Medicament(ref, libelle, prix);

        if (isUpdate) {
            // Update existing medicament
            if (listMedicament.modifierMedicament(newMedicament)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament updated successfully!");
                clearFields();
                loadMedicaments();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update Medicament.");
            }
        } else {
            // Create new medicament
            if (listMedicament.ajouterMedicament(newMedicament)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament created successfully!");
                clearFields();
                loadMedicaments();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create Medicament.");
            }
        }
        selectedMedicament = null; // Reset selectedMedicament after save or update
    }

    private void handleDeleteButton(ActionEvent event) {
        Medicament selectedMedicament = listmed.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            int ref = selectedMedicament.getRef();
            if (listMedicament.supprimerMedicament(ref)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medicament deleted successfully!");
                loadMedicaments();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete Medicament.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a Medicament to delete.");
        }
    }

    private void clearFields() {
        refmed.clear();
        libmed.clear();
        prixmed.clear();
    }

    private void openMminViewCopie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("min-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hospital Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMminViewLMP(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("med_pat.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Hospital Management");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
