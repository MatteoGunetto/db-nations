package org.lesson.java.nation;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        parametri connessione
        String url = "jdbc:mysql://localhost:8889/db_nations";
        String user = "root";
        String password = "root";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Scanner in = new Scanner(System.in);
            boolean exit = false;

            PreparedStatement sql = conn.prepareStatement("SELECT c.country_id, c.name, r.name, c2.name "
                    + "FROM countries c "
                    + "JOIN regions r ON c.region_id = r.region_id "
                    + "JOIN continents c2 ON r.continent_id = c2.continent_id "
                    + "ORDER BY c.name" );

            while(!exit){

                System.out.println("\n----------------------------------\n");
                System.out.print("scrivi la tua opzione: ");
                String guess = in.nextLine();

                if(guess.isBlank()) {
                    System.out.println("\n----------------------------------\n");
                    System.out.println("ciao!");
                    System.out.println("\n----------------------------------\n");
                    exit = !exit;
                    break;
                }

                System.out.println("\n...searching\n");

                ResultSet res = sql.executeQuery();


                while(res.next()) {
                    if(res.getString(2).toLowerCase().contains(guess)) {
                        System.out.println("ID: " + res.getInt(1) + " | Country: " + res.getString(2) + " | Region: " + res.getString(3) + " | Contintent: " + res.getString(4));
                    }
                }

                System.out.println("\n----------------------------------\n");
                System.out.print("scrivi l'ID per maggiori informazioni: ");
                System.out.println("\n\n----------------------------------\n");

                int idGuess = Integer.parseInt(in.nextLine());

                PreparedStatement sql2 = conn.prepareStatement("SELECT c.name, cs.population, cs.gdp, cs.`year`, l.`language`  FROM countries c "
                        + "JOIN country_stats cs ON cs.country_id = c.country_id "
                        + "JOIN country_languages cl ON cl.country_id = c.country_id "
                        + "JOIN languages l ON cl.language_id = l.language_id "
                        + "WHERE c.country_id = ? "
                        + "AND cs.`year` = ( "
                        + "SELECT MAX(year) FROM country_stats cs2 "
                        + "WHERE country_id = ?"
                        + ")"
                );

                sql2.setInt(1, idGuess);
                sql2.setInt(2, idGuess);

                ResultSet res2 = sql2.executeQuery();


                System.out.println("\n----------------------------------\n");
                res2.next();
                System.out.println("Stato: " + res2.getString(1));
                System.out.print("Lingue: ");

                int year = res2.getInt(4);
                long population = res2.getLong(2);
                long gdp = res2.getLong(3);

                int counter = 0;
                while(res2.next()) {
                    if(counter == 0) {
                        System.out.print(res2.getString(5));
                    }else {
                        System.out.print(", " + res2.getString(5));
                    }
                    counter++;
                }

                System.out.println("\nAnno: " + year);
                System.out.println("Popolazione: " + population);
                System.out.println("GDP (PIL): " + gdp + "$");
                System.out.println("\n----------------------------------\n");


            }

            in.close();

        } catch (Exception e) {
            System.err.println("\nError: " + e.getMessage());
        }

    }
}