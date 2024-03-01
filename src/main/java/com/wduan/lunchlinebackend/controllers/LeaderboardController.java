package com.wduan.lunchlinebackend.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import com.wduan.lunchlinebackend.util.Utils;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Consumer;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/api/v1/leaderboard")
public class LeaderboardController {

    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getLeaderboard() {
        JsonObject leaderboard = new JsonObject();
        HashMap<String, Integer> userCalories = new HashMap<>();
        dbHelper.getStdl().find().forEach((Consumer<Document>) document -> {
            userCalories.put((String) document.get("fullName"), (Integer) document.get("calories"));
        });
        HashMap<String, Integer> temp = Utils.sortByValue(userCalories);

        JsonArray leaderboardArray = new JsonArray();
        temp.forEach((s, integer) -> {
            JsonObject user = new JsonObject();
            user.addProperty("name", s);
            user.addProperty("calories", integer);
            leaderboardArray.add(user);
        });

        // Check if the leaderboard array is empty
        if (leaderboardArray.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Leaderboard is empty.");
        } else {
            // Return the leaderboard array
            return ResponseEntity.ok(leaderboardArray.toString());
        }
    }


}
