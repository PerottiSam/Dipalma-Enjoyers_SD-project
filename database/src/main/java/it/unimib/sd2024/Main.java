package it.unimib.sd2024;

import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import java.io.*;
import java.time.LocalDate;

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
        */
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

                switch (inputLine) {
                    case "ciao":
                        out.println("ciaoooo");
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

