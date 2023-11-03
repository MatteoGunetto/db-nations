package org.lesson.java.nation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        parametri connessione
        String url = "jdbc:mysql://localhost:8889/db_nations";
        String user = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            System.out.println("Connessione stabilita correttamente");
        } catch (Exception e) {

            System.out.println("Errore di connessione: " + e.getMessage());
        }

        System.out.println("\n----------------------------------\n");
        System.out.println("The end");
    }
}