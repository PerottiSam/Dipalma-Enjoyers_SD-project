package it.unimib.sd2024;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import it.unimib.sd2024.Entities.Domain;
import it.unimib.sd2024.Entities.User;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("domains")
public class DomainsResource{
    static private Map<String, LocalDateTime> domainsInUse = new ConcurrentHashMap<String, LocalDateTime>();

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
    public Response getDomain(@PathParam("domainName") String domainName, @QueryParam("verbose") Boolean verbose) {
        if(!verbose){
            return getDomainWithPurchaseParam(domainName);
        }else{
            String response = DBConnectionHandler.sendMessageToDB("GET domain " + domainName);

            if(response.equals("ERROR NOT_FOUND")){
                //Questa casistica viene usata anche per registrare un dominio
                //e vedere se non esiste già
                return Response.status(Status.NOT_FOUND).build();
            }else{
                return Response.ok(response).build();
            }
        }
    }

    public Response getDomainWithPurchaseParam(String domainName){
        String response = DBConnectionHandler.sendMessageToDB("GET domain " + domainName + " expirationDate");
        LocalDateTime now = LocalDateTime.now();

        if(response.equals("ERROR NOT_FOUND")){
            //Non c'è, procedo a metterlo nella hashmap di quelli in corso di acquisto
            //se non è presente nemmeno li
            LocalDateTime old = domainsInUse.get(domainName);
            if(old == null){
                domainsInUse.put(domainName, now);
            }else{
                long seconds = ChronoUnit.SECONDS.between(old, LocalDateTime.now());
                if(seconds > 180){
                    domainsInUse.put(domainName, now);
                }else{
                    return Response.ok().build();
                }
            }

            return Response.status(Status.NOT_FOUND).build();
        }else{
            //E' presente, controllo se è scaduto e quindi se è acquistabile, nel caso lo metto
            //nella hashmap se non c'è nemmeno li
            if(LocalDate.parse(response).compareTo(LocalDate.now()) < 0){
                //Scaduto
                LocalDateTime old = domainsInUse.get(domainName);
                if(old == null){
                    domainsInUse.put(domainName, now);
                }else{
                    long seconds = ChronoUnit.SECONDS.between(old, LocalDateTime.now());
                    if(seconds > 180){
                        domainsInUse.put(domainName, now);
                    }else{
                        return Response.ok().build();
                    }
                }

                return Response.status(Status.NOT_FOUND).build();
            }else{
                //Attivo
                return Response.ok().build();
            }
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
                domainsInUse.remove(domain.getDomainName());
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