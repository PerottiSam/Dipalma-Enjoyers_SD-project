package it.unimib.sd2024;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.JsonObject;

public class Database {
    public static ConcurrentHashMap<String, Document> domainsCollection;
    public static ConcurrentHashMap<String, Document> usersCollection;

    

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
