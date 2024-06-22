package it.unimib.sd2024;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DBConnectionHandler {
	private static String readFromServer(BufferedReader br) throws IOException {
		StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null && !line.equals("END")) {
            responseBuilder.append(line);
        }
        String response = responseBuilder.toString();
        return response;
	}
	
	public static String sendMessage(Socket socket, String message) throws IOException {	
		var outToServer = new PrintWriter(socket.getOutputStream(), true);
        var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String response = "";
        try {
        	outToServer.println(message);
        	response = readFromServer(in);
        } catch (Exception e) {
        	System.err.println("There was an error accessing the DB: " + e.getMessage());
        }
        return response;
	}

    public static String sendMessageToDB(String message) {
	    try {
	        Socket socket = new Socket("localhost", 3030);
	        String response = "";
			try {
				response = sendMessage(socket, message);
			} catch (IOException e) {
				System.err.println("Errore durante l'invio messaggio al DB: " + e.getMessage());
			}
	       
            socket.close();

	        return response;
	    } catch (Exception e) {
	    	System.err.println("Errore durante la connessione al DB: " + e.getMessage());
	        return "NULL";
	    }
	}
}

