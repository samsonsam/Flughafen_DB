package model;

import org.postgresql.util.ServerErrorMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class DBController {
    String url;
    Properties props;
    String user = "_s0556350__flughafen_generic";
    String password = "secretpassword";
    Connection conn;

    public DBController() {
        url = "jdbc:postgresql://db.f4.htw-berlin.de/_s0556350__flughafen";
        props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        //props.setProperty("ssl","true");
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection(url, props);
        if (!conn.isClosed()) {
            System.out.println("Erfolgreich verbunden");
        } else {
            System.out.println("Verbindungsfehler");
        }
    }

    public void printTable() throws SQLException {
        System.out.println("** Ausgabe der Tabelle **");
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM flug;");
        pstm.execute();
        ResultSet rs = pstm.getResultSet();
        System.out.format("%10s%14s%15s%24s%8s%15s%24s%8s%20s%20s",
                "Flugnummer",
                "Kapazitaet",
                "Startflughafen",
                "Zeit",
                "Gate",
                "Zielflughafen",
                "Zeit",
                "Gate",
                "Flugzeugtyp",
                "Fluggesellschaft"
        );
        System.out.print("\n");
        while (rs.next()) {
            System.out.format("%10s%14s%15s%24s%8s%15s%24s%8s%20s%20s",
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10)
            );
            System.out.print("\n");
        }
        rs.close();
    }

    public void insertIntoTable() throws Exception {
        System.out.println("** Eingabe neuer Datensätze **");
        System.out.println("Bitte geben Sie folgende Daten ein...");

        System.out.print("Flugnummer: ");
        int flugnummer = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

        System.out.print("Kapazität: ");
        int kapazitaet = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

        System.out.print("Startflughafen: ");
        String start = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Startzeit [yyyy-mm-dd hh:mm:ss]: ");
        String startZeit = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Startgate: ");
        String startGate = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Zielflughafen: ");
        String ziel = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Zielzeit [yyyy-mm-dd hh:mm:ss]: ");
        String zielZeit = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Zielgate: ");
        String zielGate = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Flugzeugtyp: ");
        String flugzeugtyp = new BufferedReader(new InputStreamReader(System.in)).readLine();

        System.out.print("Fluggesellschaft: ");
        String fluggesellschaft = new BufferedReader(new InputStreamReader(System.in)).readLine();

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO flug (flugnummer, kapazitaet, start, start_time, start_gate, ziel, ziel_time, ziel_gate, flugzeugtyp, fluggesellschaft) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, flugnummer);
        pstmt.setInt(2, kapazitaet);
        pstmt.setString(3, start);
        pstmt.setTimestamp(4, Timestamp.valueOf(startZeit));
        pstmt.setString(5, startGate);
        pstmt.setString(6, ziel);
        pstmt.setTimestamp(7, Timestamp.valueOf(zielZeit));
        pstmt.setString(8, zielGate);
        pstmt.setString(9, flugzeugtyp);
        pstmt.setString(10, fluggesellschaft);
        try {
            pstmt.execute();
        } catch (org.postgresql.util.PSQLException e) {
            ServerErrorMessage s = e.getServerErrorMessage();
            System.err.println(s.getMessage());
        }
    }

    public void deleteRow() throws SQLException, IOException {
        System.out.println("** Löschen von Datensätzen (Einfaches Löschen über den Primärschlüssel) **");
        System.out.print("Primärschlüssel: ");
        try {
            PreparedStatement pstm = conn.prepareStatement("DELETE FROM flug WHERE flugnummer  = "
                    + Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine()));
            pstm.execute();
        } catch (NumberFormatException ignored) {
            System.out.println("Abbruch...");
        }
    }

    public void navigateTable() throws SQLException, IOException {
        System.out.println("** Navigieren durch die Tabelle **\n" +
                "Bitte waehlen sie folgende Optionen...\nn: Naechste Zeile p: Vorherige Zeile .exit: Hauptmenü");
        PreparedStatement pstm = conn.prepareStatement("SELECT * FROM flug;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstm.execute();
        ResultSet rs = pstm.getResultSet();
        boolean run = true;
        while (run) {
            String opt = new BufferedReader(new InputStreamReader(System.in)).readLine();
            switch (opt) {
                case "n":
                    if (!rs.isLast()) {
                        rs.next();
                    } else {
                        System.err.println("\nEnde der Tabelle erreicht!\n");
                    }
                    break;
                case "p":
                    if (!rs.isFirst()) {
                        rs.previous();
                    } else {
                        System.err.println("\nAnfang der Tabelle erreicht!\n");
                    }
                    break;
                case ".exit":
                    run = false;
                    break;
                default:
                    System.err.println("Flasche Eingabe!");
                    break;
            }
            System.out.format("%10s%14s%15s%24s%8s%15s%24s%8s%20s%20s",
                    "Flugnummer",
                    "Kapazitaet",
                    "Startflughafen",
                    "Zeit",
                    "Gate",
                    "Zielflughafen",
                    "Zeit",
                    "Gate",
                    "Flugzeugtyp",
                    "Fluggesellschaft"
            );
            System.out.print("\n");
            System.out.format("%10s%14s%15s%24s%8s%15s%24s%8s%20s%20s",
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10)
            );
            System.out.print("\n");
        }
    }

    public void disconnect() throws SQLException {
        conn.close();
        if (conn.isClosed()) {
            System.out.println("Verbindung erfolgreich getrennt");
        }
    }
}
