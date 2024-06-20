package it.unimib.sd2024;

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

    static{
        domainsCollection.put("pippo.it", new Document("pippo.it", "{\"domainName\":\"pippo.it\",\"expirationDate\":\"2007-12-10\",\"registrationDate\":\"2003-12-13\",\"userEmail\":\"pippo@gmail.com\"}"));
        domainsCollection.put("sam.it", new Document("sam.it", "{\"domainName\":\"sam.it\",\"expirationDate\":\"2007-12-10\",\"registrationDate\":\"2003-12-13\",\"userEmail\":\"samper@gmail.com\"}"));
        domainsCollection.put("tigre.it", new Document("tigre.it", "{\"domainName\":\"tigre.it\",\"expirationDate\":\"2007-12-10\",\"registrationDate\":\"2003-12-13\",\"userEmail\":\"tigre@gmail.com\"}"));

        usersCollection.put("samper@gmail.com", new Document("samper@gmail.com", "{\"email\":\"samper@gmail.com\",\"name\":\"Samuele\",\"surname\":\"Perotti\"}"));
        usersCollection.put("pippo@gmail.com", new Document("pippo@gmail.com", "{\"email\":\"pippo@gmail.com\",\"name\":\"Pippo\",\"surname\":\"Perotti\"}"));
        usersCollection.put("tigre@gmail.com", new Document("tigre@gmail.com", "{\"email\":\"tigre@gmail.com\",\"name\":\"Tigre\",\"surname\":\"Perotti\"}"));
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

    // Metodo per rimuovere un attributo da un JsonObject (usato per rimuovere orders)
    private static JsonObject removeAttribute(JsonObject jsonObject, String attributeName) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (String key : jsonObject.keySet()) {
            if (!key.equals(attributeName)) {
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

    public static String getAllUsers(){
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
                            
        for (Document value : usersCollection.values()) {
            jsonArrayBuilder.add(removeAttribute(value.getJsonObject(), "orders"));
        }

        // Creazione di un JsonObject per contenere il JsonArray
        JsonObject combinedJson = Json.createObjectBuilder()
            .add("users", jsonArrayBuilder)
            .build();
        
        return combinedJson.toString();
    }


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
            jsonArrayBuilder.add(removeAttribute(user.getJsonObject(), "orders"));
            
            return Json.createObjectBuilder()
                    .add("domainUser", jsonArrayBuilder)
                    .build()
                    .toString();
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
}
