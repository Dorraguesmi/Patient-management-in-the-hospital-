package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class listMedicament {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hopitale?user=system&password=StrongPassword1!";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static ObservableList<Medicament> getMedicament() {
        ObservableList<Medicament> medicament = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM medicament")) {
            while (rs.next()) {
                medicament.add(new Medicament(
                        rs.getInt("ref"),
                        rs.getString("libelle"),
                        rs.getDouble("prix")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }

    public static boolean ajouterMedicament(Medicament medicament) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO medicament (ref, libelle, prix) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, medicament.getRef());
            pstmt.setString(2, medicament.getLibelle());
            pstmt.setDouble(3, medicament.getPrix());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean modifierMedicament(Medicament medicament) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("UPDATE medicament SET libelle = ?, prix = ? WHERE ref = ?")) {
            pstmt.setString(1, medicament.getLibelle());
            pstmt.setDouble(2, medicament.getPrix());
            pstmt.setInt(3, medicament.getRef());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean supprimerMedicament(int ref) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM medicament WHERE ref = ?")) {
            pstmt.setInt(1, ref);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ObservableList<Medicament> rechercherMedicament(String libelle) {
        ObservableList<Medicament> medicament = FXCollections.observableArrayList();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM medicament WHERE libelle LIKE ?")) {
            pstmt.setString(1, "%" + libelle + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    medicament.add(new Medicament(
                            rs.getInt("ref"),
                            rs.getString("libelle"),
                            rs.getDouble("prix")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicament;
    }
}
