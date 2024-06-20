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

import it.unimib.sd2024.Entities.*;
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

    /*
     * Implementa GET "/users".
     */
    @Path("/{email}/orders")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("email") String email) {
        return DBConnectionHandler.sendMessageToDB("GET orders " + email);
    }

    @Path("/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDomain(@PathParam("email") String email) {
		
        String response = DBConnectionHandler.sendMessageToDB("GET user " + email);
        
        if(response.equals("ERROR NOT_FOUND")){
            return Response.status(Status.NOT_FOUND).build();
        }else{
            return Response.ok(response).build();
        }
        
    }

    /**
     * Implementazione di POST "/users".
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();
        
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


    /**
     * Implementazione di POST "/users/{email}".
     */
    @Path("/{email}/orders")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(@PathParam("email") String email, Order order) {
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();
        
        String response = DBConnectionHandler.sendMessageToDB("CREATE order " + order.getId() + 
            " " + jsonb.toJson(order));

        if (response.equals("OK")){
            try {
                var uri = new URI("/users/" + email + "/orders/" + order.getId());
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