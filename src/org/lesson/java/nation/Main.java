package org.lesson.java.nation;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
//        parametri connessione
        String url = "jdbc:mysql://localhost:8889/db_nations";
        String user = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            PreparedStatement sql = conn.prepareStatement("SELECT c.country_id, c.name, r.name, c2.name "
                    + "FROM countries c "
                    + "JOIN regions r ON c.region_id = r.region_id "
                    + "JOIN continents c2 ON r.continent_id = c2.continent_id "
                    + "ORDER BY c.name" );
            ResultSet res = sql.executeQuery();

            while(res.next()) {
                System.out.println("ID: " + res.getInt(1) + " | Country: " + res.getString(2) + " | Region: " + res.getString(3) + " | Contintent: " + res.getString(4));
            }
            //System.out.println("Connessione stabilita correttamente");
        } catch (Exception e) {
            System.err.println("\nError: " + e.getMessage());
        }
    }
}