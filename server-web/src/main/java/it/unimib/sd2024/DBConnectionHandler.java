package it.unimib.sd2024;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

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
}

