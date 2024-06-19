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

import it.unimib.sd2024.entities.*;
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

@Path("users")
public class UsersResource{
    /*
     * Implementa GET "/users".
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        return DBConnectionHandler.sendMessageToDB("GET users");
    }

    /**
     * Implementazione di POST "/users".
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();

        // Bisogna inizializzare una mappa di orders vuota.
        user.setOrders(new HashMap<Integer, Order>());
        
        String response = DBConnectionHandler.sendMessageToDB("CREATE user " + user.getEmail() + 
            " " + jsonb.toJson(user));

        if (response.equals("OK")){
            try {
                var uri = new URI("/users/" + user.getEmail());
                return Response.created(uri).build();
            } catch (URISyntaxException e) {
                System.out.println(e);
                return Response.serverError().build();
            }
        }else{
            //Esiste già un utente con la email inserita
            return Response.status(Status.CONFLICT).build();
        }
    }
}