package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class listMedPat {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hopitale?user=system&password=StrongPassword1!";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static ObservableList<MedPat> getMedPat() {
        ObservableList<MedPat> medPats = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM patientmed")) {
            while (rs.next()) {
                medPats.add(new MedPat(
                        rs.getInt("ref"),
                        rs.getInt("cin")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medPats;
    }

    public static boolean ajouterMedPat(MedPat medPat) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO patientmed (ref, cin) VALUES (?, ?)")) {
            pstmt.setInt(1, medPat.getRefmed());
            pstmt.setInt(2, medPat.getCinpat());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierMedPat(MedPat medPat) {
        try (Connection conn = getConnection();
             PreparedStatement pstmtUpdate = conn.prepareStatement("UPDATE patientmed SET cin = ? WHERE ref = ?")) {

            // Update the entry
            pstmtUpdate.setInt(1, medPat.getCinpat());
            pstmtUpdate.setInt(2, medPat.getRefmed());
            pstmtUpdate.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerMedPat(int refmed) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM patientmed WHERE ref = ?")) {
            pstmt.setInt(1, refmed);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
