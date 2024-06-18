package it.unimib.sd2024;

import java.io.StringReader;

import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;

public class Document{
    private String id;
    private String jsonData;


    public Document(String id, String jsonData) {
        this.id = id;
        this.jsonData = jsonData;
    }
    

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonData() {
        return this.jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public JsonObject getJsonObject() {
        Jsonb jsonb = JsonbBuilder.create();
        try {
            return jsonb.fromJson(jsonData, JsonObject.class);
        } catch (JsonbException e) {
            e.printStackTrace();
            return null;
        }
    }
}