package com.wduan.lunchlinebackend.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wduan.lunchlinebackend.LogController;
import org.bson.Document;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static Gson gson = new Gson();
    private static final HashMap<String,Integer> calorieMap = readCalorieMap();

    public static JsonObject DocumentToJsonObject(Document doc) {
        JsonObject jsonObject = new JsonObject();
        doc.forEach((key, value) -> {
            if (value instanceof Document) {
                // If value is another document, recursively convert it to JsonObject
                jsonObject.add(key, DocumentToJsonObject((Document) value));
            } else if (value instanceof Iterable) {
                // If value is a list, recursively convert each item to JsonObject
                JsonArray jsonArray = new JsonArray();
                for (Object item : (Iterable<?>) value) {
                    if (item instanceof Document) {
                        jsonArray.add(DocumentToJsonObject((Document) item));
                    } else {
                        jsonArray.add(item.toString());
                    }
                }
                jsonObject.add(key, jsonArray);
            } else {
                // Otherwise, add the value directly
                jsonObject.addProperty(key, value.toString());
            }
        });
        return jsonObject;
    }

    public static HashMap<String,Integer> readCalorieMap() {
        FileReader reader;
        JsonObject jso;
        HashMap<String,Integer> temp = new HashMap<>();
        try {

            reader = new FileReader("src/main/resources/calories.json");
            jso = new Gson().fromJson(reader, JsonObject.class);
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(String key : jso.keySet()) {
            temp.put(key,jso.get(key).getAsInt());
        }
        return temp;
    }



    public static int getCalories(Order order) {
        System.out.println(calorieMap);
        int temp=0;
        temp+=calorieMap.get(order.getBreadType());
        for (String protein : order.getProtein()) {
            temp+=calorieMap.get(protein.substring(1,protein.length()-1));
        }
        for (String topping : order.getToppings()) {
            System.out.println(topping);
            temp+=calorieMap.get(topping.substring(1,topping.length()-1));
        }
        for (String sauce : order.getSauces()) {
            temp+=calorieMap.get(sauce.substring(1,sauce.length()-1));
        }
        return order.getSubSize()==6?temp:temp*2;
    }

    public static JsonObject hashMaptoJson(HashMap<String, Order> map) {
        JsonObject json = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (Order order : map.values()) {
            jsonArray.add(order.toJson());
        }

        json.add("orders", jsonArray);

        return json;
    }

    public static String randomEmotiGuy() {
        File folder = new File("src/main/resources/static/emotiguy");
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            Random random = new Random();
            int randomIndex = random.nextInt(files.length);
            return files[randomIndex].getName();
        } else {
            LogController.log("there aren't any images you fucking clown");
        }
        return null;
    }

    public static String toppingJoiner(JsonArray array) {
        return array.asList().stream().map(Object::toString).map(n-> n.substring(1,n.length()-1)).collect(Collectors.joining("-"));
    }

}
