package it.unimib.sd2024;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.JsonObject;

public class Database {
    public static ConcurrentHashMap<String, Document> domainsCollection = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Document> usersCollection = new ConcurrentHashMap<>();

    static{
        domainsCollection.put("pippo.it", new Document("pippo.it", "{\"domainName\":\"pippo.it\",\"expirationDate\":\"2007-12-10\",\"registrationDate\":\"2003-12-13\",\"userEmail\":\"pippo@gmail.com\"}"));
        domainsCollection.put("sam.it", new Document("sam.it", "{\"domainName\":\"sam.it\",\"expirationDate\":\"2007-12-10\",\"registrationDate\":\"2003-12-13\",\"userEmail\":\"sam@gmail.com\"}"));
        domainsCollection.put("tigre.it", new Document("tigre.it", "{\"domainName\":\"tigre.it\",\"expirationDate\":\"2007-12-10\",\"registrationDate\":\"2003-12-13\",\"userEmail\":\"tigre@gmail.com\"}"));
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
}
