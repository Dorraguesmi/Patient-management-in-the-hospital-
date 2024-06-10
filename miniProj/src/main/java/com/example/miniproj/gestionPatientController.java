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
import model.Patient;
import model.listPatient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class gestionPatientController implements Initializable {

    @FXML
    private TableView<Patient> listpt;

    @FXML
    private TableColumn<Patient, Integer> cnomp1;

    @FXML
    private TableColumn<Patient, String> cnomp;

    @FXML
    private TableColumn<Patient, String> cprenp;

    @FXML
    private TableColumn<Patient, String> cadrp;

    @FXML
    private TableColumn<Patient, String> ctelp;

    @FXML
    private TableColumn<Patient, String> cgenrep;

    @FXML
    private TextField nomp1;

    @FXML
    private TextField nomp;

    @FXML
    private TextField prenp;

    @FXML
    private TextField adrp;

    @FXML
    private TextField telp;

    @FXML
    private RadioButton gh;

    @FXML
    private RadioButton gf;

    @FXML
    private Button enregp;

    @FXML
    private Button modifp;

    @FXML
    private Button suppp;

    @FXML
    private Button impp;

    @FXML
    private Button impp1;

    //@FXML
    //private Button searchButton;

    private Patient selectedPatient; // Keep track of the selected patient for modification

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize table columns with property values from Patient class
        cnomp1.setCellValueFactory(new PropertyValueFactory<>("cin"));
        cnomp.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cprenp.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cadrp.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        ctelp.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        cgenrep.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Set button actions
        enregp.setOnAction(this::handleSaveButton);
        modifp.setOnAction(this::handleUpdateButton);
        suppp.setOnAction(this::handleDeleteButton);
        impp.setOnAction(this::openMminViewCopie);
        impp1.setOnAction(this::openMminViewMed);

        loadPatients(); // Load patients from database when initializing the controller
    }

    private void loadPatients() {
        ObservableList<Patient> patients = listPatient.getPatients();
        if (patients != null) {
            listpt.setItems(patients);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load patients.");
        }
    }

    private void handleUpdateButton(ActionEvent event) {
        selectedPatient = listpt.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            nomp1.setText(String.valueOf(selectedPatient.getCin()));
            nomp.setText(selectedPatient.getNom());
            prenp.setText(selectedPatient.getPrenom());
            adrp.setText(selectedPatient.getAdresse());
            telp.setText(selectedPatient.getTelephone());
            if ("Homme".equals(selectedPatient.getGenre())) {
                gh.setSelected(true);
                gf.setSelected(false);
            } else {
                gh.setSelected(false);
                gf.setSelected(true);
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a patient to update.");
        }
    }

    private void handleSaveButton(ActionEvent event) {
        boolean isUpdate = selectedPatient != null; // Check if an existing patient is being updated

        String nom = nomp.getText();
        String prenom = prenp.getText();
        String genre = gh.isSelected() ? "Homme" : "Femme";
        String tel = telp.getText();
        String adresse = adrp.getText();
        int cin = Integer.parseInt(nomp1.getText());

        Patient newPatient = new Patient(cin, nom, prenom, adresse, tel, genre);

        if (isUpdate) {
            // Update existing patient
            if (listPatient.modifierPatients(selectedPatient, nom, prenom, genre, tel, adresse, cin)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient updated successfully!");
                clearFields();
         } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update patient.");
            }
        } else {
            // Create new patient
            if (listPatient.ajouterPatient(newPatient)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient created successfully!");
                clearFields();
                loadPatients();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to create patient.");
            }
        }
        selectedPatient = null; // Reset selectedPatient after save or update
    }

    private void handleDeleteButton(ActionEvent event) {
        Patient selectedPatient = listpt.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            int cin = selectedPatient.getCin();
            if (listPatient.supprimerPatient(cin)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Patient deleted successfully!");
                loadPatients();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete patient.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a patient to delete.");
        }
    }

    private void clearFields() {
        nomp.clear();
        nomp1.clear();
        prenp.clear();
        adrp.clear();
        telp.clear();
        gh.setSelected(false);
        gf.setSelected(false);
    }

    private void openMminViewCopie(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproj/mmin-view - Copie.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Hospital Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Mmin View Copie.");
        }
    }
    private void openMminViewMed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproj/medicament.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Hospital Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to open Mmin View Copie.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
