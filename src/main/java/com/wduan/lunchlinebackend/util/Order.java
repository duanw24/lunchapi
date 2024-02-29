package com.wduan.lunchlinebackend.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class Order {
    private long id;
    private String name;
    private String email;
    private int studentID;
    private int lunchPeriod;
    private String breadType;
    private int subSize;
    private boolean toasted;
    private String[] protein;
    private String[] toppings;
    private String[] sauces;

    private long timestamp;

    public Order(long id, long timestamp, String name, String email, int studentID, int lunchPeriod, String breadType, int subSize, boolean toasted, String[] protein, String[] toppings, String[] sauces) {
        this.name = name;
        this.email = email;
        this.studentID = studentID;
        this.lunchPeriod = lunchPeriod;
        this.breadType = breadType;
        this.subSize = subSize;
        this.toasted = toasted;
        this.protein = protein;
        this.toppings = toppings;
        this.sauces = sauces;

        this.timestamp = timestamp;
        this.id=id;

    }

    public JsonObject toJson() {
        return Utils.gson.fromJson("{\n" +
                "  \"id\": "+id+",\n" +
                "  \"fullName\": \""+name+"\",\n" +
                "  \"studentID\": "+studentID+",\n" +
                "  \"email\": \""+email+"\",\n" +
                "  \"lunchPeriod\": "+lunchPeriod+",\n" +
                "  \"order\": {\n" +
                "      \"timestamp\":"+timestamp+",\n" +
                "      \"breadType\": \""+breadType+"\",\n" +
                "      \"toasted\": "+toasted+",\n" +
                "      \"subSize\": "+ subSize +",\n" +
                "      \"protein\": "+ Arrays.toString(protein) +"\n" +
                "      \"topping\": "+ Arrays.toString(toppings) +",\n" +
                "      \"sauce\": "+ Arrays.toString(sauces) +" \n" +
                "  },\n" +
                "  \"calories\": "+Utils.getCalories(this)+"\n" +
                "}",JsonObject.class);
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"fullName\": \"" + name + "\",\n" +
                "  \"studentID\": " + studentID + ",\n" +
                "  \"email\": \"" + email + "\",\n" +
                "  \"lunchPeriod\": " + lunchPeriod + ",\n" +
                "  \"order\": {\n" +
                "      \"timestamp\":" + timestamp + ",\n" +
                "      \"breadType\": \"" + breadType + "\",\n" +
                "      \"toasted\": " + toasted + ",\n" +
                "      \"subSize\": " + subSize + ",\n" +
                "      \"protein\": " + Arrays.toString(protein) + "\n" +
                "      \"topping\": " + Arrays.toString(toppings) + ",\n" +
                "      \"sauce\": " + Arrays.toString(sauces) + " \n" +
                "  },\n" +
                "  \"calories\": "+Utils.getCalories(this)+"\n" +
                "}";
    }
}
