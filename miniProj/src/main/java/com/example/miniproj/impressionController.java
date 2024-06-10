package com.example.miniproj;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;
import model.listPatient;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class impressionController {
    @FXML
    private TableView<Patient> tableImp;

    @FXML
    private TableColumn<Patient, Integer> nomImp1;

    @FXML
    private TableColumn<Patient, String> nomImp;

    @FXML
    private TableColumn<Patient, String> prenImp;

    @FXML
    private TableColumn<Patient, String> adrImp;

    @FXML
    private TableColumn<Patient, String> telImp;

    @FXML
    private TableColumn<Patient, String> genreImp;

    @FXML
    private TextField nomIp1;

    @FXML
    private TextField nomIp;

    @FXML
    private TextField prenIp;

    @FXML
    private TextField adrIp;

    @FXML
    private TextField telIp;

    @FXML
    private RadioButton ghIp;

    @FXML
    private RadioButton gfIp;

    @FXML
    private Button valideIp;

    @FXML
    private Button suppIp;

    private Patient selectedPatient;

    public void initialize() {
        // Initialize table columns
        nomImp1.setCellValueFactory(new PropertyValueFactory<>("cin"));
        nomImp.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenImp.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adrImp.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telImp.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        genreImp.setCellValueFactory(new PropertyValueFactory<>("genre"));

        // Set button actions
        valideIp.setOnAction(event -> handleValiderButton());
        suppIp.setOnAction(event -> handleCloseButton());

        // Load patients from database
        loadPatients();

        // Handle selection change in TableView
        tableImp.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedPatient = newSelection;
                displayPatientInformation(selectedPatient);
            }
        });
    }

    private void loadPatients() {
        // Load patients from the database and populate the TableView
        // You'll need to implement this method
        ObservableList<Patient> patients = listPatient.getPatients();
        if (patients != null) {
            tableImp.setItems(patients);
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load patients.");
        }
    }

    private void displayPatientInformation(Patient patient) {
        if (patient != null) {
            nomIp1.setText(String.valueOf(patient.getCin()));
            nomIp.setText(patient.getNom());
            prenIp.setText(patient.getPrenom());
            adrIp.setText(patient.getAdresse());
            telIp.setText(patient.getTelephone());
            if ("Homme".equals(patient.getGenre())) {
                ghIp.setSelected(true);
                gfIp.setSelected(false);
            } else {
                ghIp.setSelected(false);
                gfIp.setSelected(true);
            }
        }
    }

    private void handleValiderButton() {
        if (selectedPatient != null) {
            // Generate PDF for selected patient
            generatePDF(selectedPatient);
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a patient.");
        }
    }

    private void generatePDF(Patient patient) {
        // Generate PDF code here (e.g., using a library like iText PDF)
        File pdfFile = new File("patient_info.pdf");
        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            // For demonstration purposes, let's write some content to the PDF file
            fos.write("Patient Information\n".getBytes());
            fos.write(("CIN: " + patient.getCin() + "\n").getBytes());
            fos.write(("Nom: " + patient.getNom() + "\n").getBytes());
            fos.write(("Prenom: " + patient.getPrenom() + "\n").getBytes());
            fos.write(("Adresse: " + patient.getAdresse() + "\n").getBytes());
            fos.write(("Telephone: " + patient.getTelephone() + "\n").getBytes());
            fos.write(("Genre: " + patient.getGenre() + "\n").getBytes());

            showAlert(Alert.AlertType.INFORMATION, "Success", "PDF created successfully!");
            downloadPDF(pdfFile); // Call the method to download and open the PDF
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Exception occurred: " + e.getMessage());
        }
    }

    private void downloadPDF(File pdfFile) {
        try {
            // Specify the download directory
            File downloadDir = new File(System.getProperty("user.home") + File.separator + "Downloads");

            // Create the download directory if it doesn't exist
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }

            // Copy the PDF file to the download directory
            Files.copy(pdfFile.toPath(), new File(downloadDir, pdfFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Open the downloaded PDF file
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(downloadDir, pdfFile.getName()));
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Desktop not supported, cannot open PDF.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to download or open PDF: " + e.getMessage());
        }
    }

    private void handleCloseButton() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Are you sure you want to close the window?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Close the window
            tableImp.getScene().getWindow().hide();
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
