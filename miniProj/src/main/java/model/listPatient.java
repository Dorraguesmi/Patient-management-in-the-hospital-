package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class listPatient {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hopitale?user=system&password=StrongPassword1!";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static ObservableList<Patient> getPatients() {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patients")) {
            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("cin"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("genre"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public static boolean ajouterPatient(Patient patient) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO patients (cin, nom, prenom, genre, telephone, adresse) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setInt(1, patient.getCin());
            pstmt.setString(2, patient.getNom());
            pstmt.setString(3, patient.getPrenom());
            pstmt.setString(4, patient.getGenre());
            pstmt.setString(5, patient.getTelephone());
            pstmt.setString(6, patient.getAdresse());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierPatients(Patient selectedPatient, String nom, String prenom, String genre, String telephone, String adresse, int cin) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE patients SET nom = ?, prenom = ?, genre = ?, telephone = ?, adresse = ? WHERE cin = ?")) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, genre);
            pstmt.setString(4, telephone);
            pstmt.setString(5, adresse);
            pstmt.setInt(6, cin);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerPatient(int cin) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM patients WHERE cin = ?")) {
            pstmt.setInt(1, cin);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Patient> rechercherPatients(String nom) {
        ObservableList<Patient> patients = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM patients WHERE nom LIKE ?")) {
            pstmt.setString(1, "%" + nom + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    patients.add(new Patient(
                            rs.getInt("cin"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("genre"),
                            rs.getString("telephone"),
                            rs.getString("adresse")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}

