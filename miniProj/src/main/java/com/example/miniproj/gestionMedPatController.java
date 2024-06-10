package com.example.miniproj;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MedPat;
import model.listMedPat;

import java.net.URL;
import java.util.ResourceBundle;

public class gestionMedPatController implements Initializable {

    @FXML
    private TableView<MedPat> listmedpat;

    @FXML
    private TableColumn<MedPat, Integer> crefmed;

    @FXML
    private TableColumn<MedPat, Integer> ccinpat;

    @FXML
    private TextField refmedTextField;

    @FXML
    private TextField cinpatTextField;

    @FXML
    private Button enregp;

    @FXML
    private Button modifp;

    @FXML
    private Button suppp;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        crefmed.setCellValueFactory(new PropertyValueFactory<>("refmed"));
        ccinpat.setCellValueFactory(new PropertyValueFactory<>("cinpat"));

        enregp.setOnAction(this::handleSaveButton);
        modifp.setOnAction(this::handleUpdateButton);
        suppp.setOnAction(this::handleDeleteButton);

        loadMedPats();
    }

    private void loadMedPats() {
        ObservableList<MedPat> medPats = listMedPat.getMedPat();
        if (medPats == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Medical patients list is null.");
        } else {
            listmedpat.setItems(medPats);
        }
    }

    private void handleUpdateButton(ActionEvent event) {
        MedPat selectedMedPat = listmedpat.getSelectionModel().getSelectedItem();
        if (selectedMedPat != null) {
            try {
                int refMed = Integer.parseInt(refmedTextField.getText());
                int cinPat = Integer.parseInt(cinpatTextField.getText());
                if (listMedPat.modifierMedPat(selectedMedPat)) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Medical patient updated successfully.");
                    loadMedPats();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update medical patient.");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numerical values for reference and patient ID.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a medical patient to update.");
        }
    }

    private void handleSaveButton(ActionEvent event) {
        try {
            int refMed = Integer.parseInt(refmedTextField.getText());
            int cinPat = Integer.parseInt(cinpatTextField.getText());
            MedPat newMedPat = new MedPat(refMed, cinPat);
            if (listMedPat.ajouterMedPat(newMedPat)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical patient saved successfully.");
                loadMedPats();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save medical patient.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter valid numerical values for reference and patient ID.");
        }
    }

    private void handleDeleteButton(ActionEvent event) {
        MedPat selectedMedPat = listmedpat.getSelectionModel().getSelectedItem();
        if (selectedMedPat != null) {
            if (listMedPat.supprimerMedPat(selectedMedPat.getRefmed())) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical patient deleted successfully.");
                loadMedPats();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete medical patient.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a medical patient to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
