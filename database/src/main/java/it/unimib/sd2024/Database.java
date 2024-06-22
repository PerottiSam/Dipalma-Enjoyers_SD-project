package it.unimib.sd2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class Database {
    public static ConcurrentHashMap<String, Document> domainsCollection = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Document> usersCollection = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Document> ordersCollection = new ConcurrentHashMap<>();

    static{
        caricaDaFile();
    }

    public static void caricaDaFile(){
        String csvFile = "sampleData.csv";
        String line = "";
        String csvSplitBy = ";"; // Separatore dei campi nel CSV
        int counter = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                if (data.length >= 2) {
                    String id = data[0];
                    String jsonData = data[1];
                    
                    if(counter < 3){
                        //domains
                        domainsCollection.put(id, new Document(id, jsonData));
                    }else if(counter < 6){
                        //users
                        usersCollection.put(id, new Document(id, jsonData));
                    }else{
                        //orders
                        ordersCollection.put(id, new Document(id, jsonData));
                    }
                } else {
                    System.err.println("Error: " + line);
                }

                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Document> findDocumentsByAttribute(ConcurrentHashMap<String, Document> collection, String attribute, String value) {
        List<Document> matchingDocuments = new ArrayList<>();
        for (Document doc : collection.values()) {
            JsonObject jsonObject = doc.getJsonObject();
            if (jsonObject.containsKey(attribute) && jsonObject.getString(attribute).equals(value)) {
                matchingDocuments.add(doc);
            }
        }
        return matchingDocuments;
    }

    // Metodo per ottenere il valore di un attributo dal JsonObject
    private static String getAttributeValue(JsonObject jsonObject, String attributeName) {
        JsonValue jsonValue = jsonObject.get(attributeName);
        return jsonValue != null ? jsonValue.toString() : null;
    }

    private static JsonObject modifyAttribute(JsonObject jsonObject, String attributeName, Object newValue) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (String key : jsonObject.keySet()) {
            if (key.equals(attributeName)) {
                if (newValue instanceof String) {
                    builder.add(key, ((String) newValue).replaceAll("\"", ""));
                }
                //... Altri tipi non implementati
            } else {
                builder.add(key, jsonObject.get(key));
            }
        }

        return builder.build();
    }

    public static String getAllDomains(){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                            
        for (Document value : Database.domainsCollection.values()) {
            jsonArrayBuilder.add(value.getJsonObject());
        }

        // Creazione di un JsonObject per contenere il JsonArray
        JsonObject combinedJson = Json.createObjectBuilder()
            .add("domains", jsonArrayBuilder)
            .build();
        
        return combinedJson.toString();
    }

    public static String getUserDomains(String userEmail){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        
        for (Document value : findDocumentsByAttribute(domainsCollection, "userEmail", userEmail)) {
            jsonArrayBuilder.add(value.getJsonObject());
        }

        // Creazione di un JsonObject per contenere il JsonArray
        JsonObject combinedJson = Json.createObjectBuilder()
            .add("domains", jsonArrayBuilder)
            .build();
        
        return combinedJson.toString();
    }

    public static String editDomain(String domainName, String attribute, String value){
        Document domain = domainsCollection.get(domainName);

        if(domain != null){
            String jsonData = modifyAttribute(domain.getJsonObject(), attribute, value).toString();

            domainsCollection.put(domainName, new Document(domainName, jsonData));

            return "OK";
        }else{
            return "ERRORE NOT_FOUND";
        }
    }

    public static String getAllUsers(){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                
        // Creazione di un JsonObject per contenere il JsonArray
        JsonObject combinedJson = Json.createObjectBuilder()
            .add("users", jsonArrayBuilder)
            .build();
        
        return combinedJson.toString();
    }

    public static String getAllOrders(String userEmail){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        
        for (Document value : findDocumentsByAttribute(ordersCollection, "userEmail", userEmail)) {
            jsonArrayBuilder.add(value.getJsonObject());
        }

        // Creazione di un JsonObject per contenere il JsonArray
        JsonObject combinedJson = Json.createObjectBuilder()
            .add("orders", jsonArrayBuilder)
            .build();
        
        return combinedJson.toString();
    }


    //Viene usato per ottenere anche i dati dell'utente proprietario del dominio
    public static String getDomainWithUser(String domainName){
        Document domain = domainsCollection.get(domainName);
        Document user;
        
        if(domain == null){
            return "ERROR NOT_FOUND";
        }else{
            String userEmail = getAttributeValue(domain.getJsonObject(), "userEmail");
            userEmail = userEmail.replaceAll("\"", "");
            user = usersCollection.get(userEmail);
            
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            jsonArrayBuilder.add(domain.getJsonObject());
            jsonArrayBuilder.add(user.getJsonObject());
            
            return Json.createObjectBuilder()
                    .add("domainUser", jsonArrayBuilder)
                    .build()
                    .toString();
        }
    }

    public static String getUser(String email){
        Document user = usersCollection.get(email);

        if(user == null){
            return "ERROR NOT_FOUND";
        }else{
            return user.getJsonObject().toString();
        }
    }

    public static String getDomainAttribute(String domainName, String attribute){
        Document domain = domainsCollection.get(domainName);
        
        if(domain == null){
            return "ERROR NOT_FOUND";
        }else{
            return getAttributeValue(domain.getJsonObject(), attribute).replaceAll("\"", "");
        }
    }

    public static String addUser(String userEmail, String jsonStringUser){
        if(!usersCollection.containsKey(userEmail)){
            usersCollection.put(userEmail, 
                new Document(userEmail, jsonStringUser));
            return "OK";
        }else{
            return "ERROR USER_EMAIL_CONFLICT";
        }
    }

    public static String addDomain(String domainName, String jsonStringDomain){
        Document doc =  new Document(domainName, jsonStringDomain);
        domainsCollection.put(domainName, doc);
        return "OK";
    }

    public static String addOrder(String idOrder, String jsonStringOrder){
        Document doc =  new Document(idOrder, jsonStringOrder);
        ordersCollection.put(idOrder, doc);

        return "OK";
    }
}
