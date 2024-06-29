package it.unimib.sd2024;

import java.net.URI;
import java.net.URISyntaxException;

import it.unimib.sd2024.Entities.*;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("users")
public class UsersResource {
    /**
     * Gestisce le richieste GET per recuperare tutti gli utenti.
     * 
     * @return una stringa JSON che rappresenta tutti gli utenti.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsers() {
        return DBConnectionHandler.sendMessageToDB("GET users");
    }

    /**
     * Gestisce le richieste GET per recuperare tutti gli ordini di un utente specifico.
     * 
     * @param email l'email dell'utente di cui recuperare gli ordini.
     * @return una stringa JSON che rappresenta gli ordini dell'utente.
     */
    @Path("/{email}/orders")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(@PathParam("email") String email) {
        return DBConnectionHandler.sendMessageToDB("GET orders " + email);
    }

    /**
     * Gestisce le richieste GET per recuperare tutti i domini di un utente specifico.
     * 
     * @param email l'email dell'utente di cui recuperare i domini.
     * @return una stringa JSON che rappresenta i domini dell'utente.
     */
    @Path("/{email}/domains")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDomains(@PathParam("email") String email) {
        return DBConnectionHandler.sendMessageToDB("GET domains " + email);
    }

    /**
     * Gestisce le richieste GET per recuperare informazioni su un utente specifico.
     * 
     * @param email l'email dell'utente da recuperare.
     * @return una Response con le informazioni sull'utente.
     */
    @Path("/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDomain(@PathParam("email") String email) {

        String response = DBConnectionHandler.sendMessageToDB("GET user " + email);

        if (response.equals("ERROR NOT_FOUND")) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok(response).build();
        }

    }

    /**
     * Gestisce le richieste POST per aggiungere un nuovo utente.
     * 
     * @param user l'utente da aggiungere.
     * @return una Response che indica il risultato dell'operazione.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) {
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();

        String response = DBConnectionHandler.sendMessageToDB("CREATE user " + user.getEmail() +
                " " + jsonb.toJson(user));

        if (response.equals("OK")) {
            try {
                var uri = new URI("/users/" + user.getEmail());
                return Response.created(uri).build();
            } catch (URISyntaxException e) {
                System.out.println(e);
                return Response.serverError().build();
            }
        } else {
            // Esiste già un utente con la email inserita
            return Response.status(Status.CONFLICT).build();
        }
    }

    /**
     * Gestisce le richieste POST per aggiungere un nuovo ordine di un utente specifico.
     * 
     * @param email l'email dell'utente a cui aggiungere l'ordine.
     * @param order l'ordine da aggiungere.
     * @return una Response che indica il risultato dell'operazione.
     */
    @Path("/{email}/orders")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addOrder(@PathParam("email") String email, Order order) {
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();

        String response = DBConnectionHandler.sendMessageToDB("CREATE order " + order.getId() +
                " " + jsonb.toJson(order));

        if (response.equals("OK")) {
            try {
                var uri = new URI("/users/" + email + "/orders/" + order.getId());
                return Response.created(uri).build();
            } catch (URISyntaxException e) {
                System.out.println(e);
                return Response.serverError().build();
            }
        } else {
            // Esiste già un utente con la email inserita
            return Response.status(Status.CONFLICT).build();
        }
    }
}