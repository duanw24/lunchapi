package com.wduan.lunchlinebackend.helpers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.*;
import com.mongodb.client.*;
import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.util.Order;
import com.wduan.lunchlinebackend.util.Utils;
import lombok.Getter;

import org.bson.Document;

import java.util.function.Consumer;

public class dbHelper {
    @Getter
    static MongoClient mongoClient;
    @Getter
    static MongoCollection<Document> d0,stdl;

    static String connectionString = "mongodb+srv://wduan:an416hk94@lunchcluster.aulglyi.mongodb.net/?retryWrites=true&w=majority";
    static ServerApi serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build();
    static MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .serverApi(serverApi)
            .build();


    public static void init() {
        mongoClient = MongoClients.create(settings);
        try {
            // Send a ping to confirm a successful connection
            MongoDatabase database = mongoClient.getDatabase("lunchLine");
            database.runCommand(new Document("ping", 1));
            LogController.log("Pinged your deployment. You successfully connected to MongoDB!");

            d0 = database.getCollection("d0");
            stdl = database.getCollection("stdl");
            LogController.log("d0/stdl located");

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void submitOrder(Order order) {
        d0.insertOne(Document.parse(order.toString()));
        LogController.log("Order id= " + order.getId() + " added to d0");
    }

    public static JsonObject getOrders() {
        FindIterable<Document> documents = d0.find();
        JsonArray jsonArray = new JsonArray();
        for (Document doc : documents) {
            jsonArray.add(Utils.DocumentToJsonObject(doc));
        }
        JsonObject jso = new JsonObject();
        jso.add("orders", jsonArray);
        return jso;
    }


    /*
        * Convert a MongoDB Document to a JSON Object
        * goofy ahh chatgpt written code
     */

}
