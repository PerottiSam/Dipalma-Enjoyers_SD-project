package it.unimib.sd2024;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("domains")
public class DomainsResource{
    

    private static String sendMessageToDB(String message) {
	    try {
	        Socket socket = new Socket("localhost", 3030);
	        String response = "";
			try {
				response = DBConnectionHandler.sendMessage(socket, message);
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

    /*
     * Implementa GET "/domains".
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDomains() {
        return sendMessageToDB("GET domains");
    }

	/**
     * Implementazione di GET "/domains/{domainName}". 
	 * Dove id è la stringa rappresentante il dominio stesso
     */
    @Path("/{domainName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDomain(@PathParam("domainName") String domainName) {
		String response = sendMessageToDB("GET domain " + domainName);

        if(response.equals("ERROR NOT_FOUND")){
			//Questa casistica viene usata anche per registrare un dominio
			//e vedere se non esiste già
			return Response.status(Status.NOT_FOUND).build();
		}else{
			return Response.ok(response).build();
		}
    }

	
}