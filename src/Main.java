import model.DBController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * Created by samuelerb on 20.06.17.
 * <p>
 * Project name: Flughafen_db
 */
public class Main {
    public static void main(String[] args) {
        boolean run = true;
        String opt = ".help";
        DBController controller = new DBController();
        try {
            controller.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("******* Wilkommen im Datenbank Interface für die Datenbank Flughafen *******\n" +
                "Geben Sie .help ein um Hinweise zur Benutzung zu bekommen.");
        while (run) {
            switch (opt) {
                case ".output":
                    try {
                        controller.printTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case ".input":
                    try {
                        controller.insertIntoTable();
                    } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());;
                    }
                    break;
                case ".delete":
                    try {
                        controller.deleteRow();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ".navigate":
                    try {
                        controller.navigateTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ".exit":
                    try {
                        controller.disconnect();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.exit(0);
                    break;
                case ".help":
                    System.out.println(
                            ".output        Ausgabe der Tabelle (Einfache Ausgabe aller Einträge)\n" +
                            ".input         Eingabe neuer Datensätze (Eingabe aller einzelnen Spalten)\n" +
                            ".delete        Löschen von Datensätzen (Einfaches Löschen über den Primärschlüssel)\n" +
                            ".navigate      Navigieren durch die Tabelle mittels n (next) und p (previous) kann sich jeder Datensatz einzeln angezeigt werden.\n" +
                            ".help          Anzeigen dieser Hilfe\n" +
                            ".exit          Beenden");
                    break;
                default:
                    break;
            }
            System.out.print("flughafen_db> ");
            try {
                opt = new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
