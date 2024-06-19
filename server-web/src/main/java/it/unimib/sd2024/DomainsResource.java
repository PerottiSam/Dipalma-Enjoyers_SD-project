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

import it.unimib.sd2024.entities.Domain;
import it.unimib.sd2024.entities.User;
import jakarta.json.bind.Jsonb;
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
    /*
     * Implementa GET "/domains".
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDomains() {
        return DBConnectionHandler.sendMessageToDB("GET domains");
    }

	/**
     * Implementazione di GET "/domains/{domainName}". 
	 * Dove id è la stringa rappresentante il dominio stesso
     */
    @Path("/{domainName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDomain(@PathParam("domainName") String domainName) {
		String response = DBConnectionHandler.sendMessageToDB("GET domain " + domainName);

        if(response.equals("ERROR NOT_FOUND")){
			//Questa casistica viene usata anche per registrare un dominio
			//e vedere se non esiste già
			return Response.status(Status.NOT_FOUND).build();
		}else{
			return Response.ok(response).build();
		}
    }

	/**
     * Implementazione di POST "/domains".
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addDomain(Domain domain) {
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();
        
        String response = DBConnectionHandler.sendMessageToDB("CREATE domain " + domain.getDomainName() + 
            " " + jsonb.toJson(domain));

        if (response.equals("OK")){
            try {
                var uri = new URI("/domains/" + domain.getDomainName());
                return Response.created(uri).build();
            } catch (URISyntaxException e) {
                System.out.println(e);
                return Response.serverError().build();
            }
        }else{
            //Il dominio è già registrato
            return Response.status(Status.CONFLICT).build();
        }
    }
}