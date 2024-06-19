package it.unimib.sd2024;

import java.net.*;
import java.time.LocalDate;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import java.io.*;


/**
 * Classe principale in cui parte il database.
 */
public class Main {
    /**
     * Porta di ascolto.
     */
    public static final int PORT = 3030;

    /**
     * Avvia il database e l'ascolto di nuove connessioni.
     */
    public static void startServer() throws IOException { 
        var server = new ServerSocket(PORT);

        System.out.println("Database listening at localhost:" + PORT);

        try {
            while (true)
                new Handler(server.accept()).start();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            server.close();
        }
    }

    /**
     * Handler di una connessione del client.
     */
    private static class Handler extends Thread {
        private Socket client;

        public Handler(Socket client) {
            this.client = client;
        }

        public void run() {
            try {
                var out = new PrintWriter(client.getOutputStream(), true);
                var in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String inputLine = in.readLine();
                String[] parts = inputLine.split(" ");
                String command = parts[0];

                switch (command) {
                    case "GET":
                        switch (parts[1]) {
                            case "domains":
                                out.println(Database.getAllDomains());
                                out.println("END");
                                break;

                            case "domain":
                                out.println(Database.getDomainWithUser(parts[2]));
                                out.println("END");
                                break;

                            case "users":
                                out.println(Database.getAllUsers());
                                out.println("END");
                                break;
                        
                            default:
                                break;
                        }
                        break;

                    case "CREATE":
                        switch (parts[1]) {
                            case "user":
                                out.println(Database.addUser(parts[2], parts[3]));
                                out.println("END");
                                break;
                        
                            default:
                                break;
                        }
                        break;
                
                    default:
                        break;
                }

                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Metodo principale di avvio del database.
     *
     * @param args argomenti passati a riga di comando.
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        startServer();
    }
}

