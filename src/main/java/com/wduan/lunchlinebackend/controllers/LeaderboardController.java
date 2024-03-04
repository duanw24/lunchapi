package com.wduan.lunchlinebackend.controllers;

import com.google.gson.JsonObject;
import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/api/v1/leaderboard")
public class LeaderboardController {

    @SuppressWarnings("unchecked")
    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getLeaderboard(HttpServletRequest request) {
        LogController.log("GET /api/v1/leaderboard from ip: " + request.getRemoteAddr());
        JsonObject leaderboard = new JsonObject();

        ArrayList<Pair<String, Integer>> t1 = new ArrayList<>();
        ArrayList<Pair<String, Integer>> ft1 = t1;
        dbHelper.getStdl().find().forEach(document -> ft1.add(new Pair<>(document.get("fullName").toString(), Integer.parseInt(document.get("calories").toString()))));
        ft1.sort(Comparator.comparingInt(Pair::getValue));
        Collections.reverse(ft1);
        JsonObject calorieLB = new JsonObject();

        if(t1.size()>100) {t1 = new ArrayList<>(t1.subList(0,100));}
        t1.forEach(n->calorieLB.addProperty(n.getKey(),n.getValue()));

        t1.clear();
        ft1.clear();

        dbHelper.getStdl().find().forEach(document -> ft1.add(new Pair<>(document.get("fullName").toString(),((List<Document>)document.get("orderHistory")).size())));
        ft1.sort(Comparator.comparingInt(Pair::getValue));
        Collections.reverse(ft1);

        if(t1.size()>100) {t1 = new ArrayList<>(t1.subList(0,100));}
        JsonObject orderLB = new JsonObject();
        t1.forEach(n-> orderLB.addProperty(n.getKey(),n.getValue()));


        leaderboard.add("calories", calorieLB);
        leaderboard.add("orders", orderLB);

        return ResponseEntity.ok(leaderboard.toString());
    }


}
