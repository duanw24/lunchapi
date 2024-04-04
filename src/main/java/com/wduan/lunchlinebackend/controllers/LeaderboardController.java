package com.wduan.lunchlinebackend.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import jakarta.servlet.http.HttpServletRequest;
import javafx.util.Pair;
import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/v1/leaderboard")
public class LeaderboardController {

    private JsonObject leaderboard;
    private boolean flag = false;

    @GetMapping(path="/refresh", produces = "text/plain")
    public ResponseEntity<Object> resetLeaderboard(HttpServletRequest request) {
        LogController.log("GET /v1/leaderboard/refresh from ip: " + request.getRemoteAddr());
        leaderboard = new JsonObject();
        flag=false;
        return ResponseEntity.ok("Leaderboard reset");
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Object> getLeaderboard(HttpServletRequest request) {
        LogController.log("GET /v1/leaderboard from ip: " + request.getRemoteAddr());

        return ResponseEntity.ok(getLeaderboard());
    }

    @GetMapping(path="/raw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getRawLeaderboard(HttpServletRequest request) {
        LogController.log("GET /v1/leaderboard/raw from ip: " + request.getRemoteAddr());
        return ResponseEntity.ok(leaderboard);
    }


    private String getLeaderboard() {
        JsonObject leaderboard=this.leaderboard;
        if (!this.flag) {
            leaderboard = new JsonObject();
            ArrayList<Pair<String, Integer>> t1 = new ArrayList<>();
            ArrayList<Pair<String, Integer>> ft1 = t1;
            dbHelper.getStdl().find().forEach(document -> ft1.add(new Pair<>(document.get("fullName").toString(), Integer.parseInt(document.get("calories").toString()))));
            ft1.sort(Comparator.comparingInt(Pair::getValue));
            Collections.reverse(ft1);
            JsonObject calorieLB = new JsonObject();

            if (t1.size() > 100) {
                t1 = new ArrayList<>(t1.subList(0, 100));
            }
            t1.forEach(n -> calorieLB.addProperty(n.getKey(), n.getValue()));

            t1.clear();
            ft1.clear();
            dbHelper.getStdl().find().forEach(document -> ft1.add(new Pair<>(document.get("fullName").toString(), ((List<Document>) document.get("orderHistory")).size())));
            ft1.sort(Comparator.comparingInt(Pair::getValue));
            Collections.reverse(ft1);

            if (t1.size() > 100) {
                t1 = new ArrayList<>(t1.subList(0, 100));
            }
            JsonObject orderLB = new JsonObject();
            t1.forEach(n -> orderLB.addProperty(n.getKey(), n.getValue()));


            leaderboard.add("calories", calorieLB);
            leaderboard.add("orders", orderLB);

            this.leaderboard=leaderboard;

            flag = true;
        }
        JsonObject calorieLB = leaderboard.get("calories").getAsJsonObject();
        JsonObject orderLB = leaderboard.get("orders").getAsJsonObject();
        StringBuilder sb = new StringBuilder(l1);
        AtomicInteger temp = new AtomicInteger(1);
        calorieLB.entrySet().forEach(n -> {
            sb.append(" <div class=\"leaderboard-spot\">\n" +
                    "            <p class=\"item\" style=\"text-align: left; margin-left: 10%\" >"+temp.getAndIncrement()+"</p>\n" +
                    "            <p class=\"item\" style=\"text-align: center\">"+n.getKey()+"</p>\n" +
                    "            <p class=\"item\" style=\"text-align: right; margin-right: 10%\">"+n.getValue()+" Calories Consumed</p>\n" +
                    "        </div>");
        });
        sb.append(l2);
        temp.set(1);
        orderLB.entrySet().forEach(n->{
            sb.append(" <div class=\"leaderboard-spot\">\n" +
                    "            <p class=\"item\" style=\"text-align: left; margin-left: 10%\" >"+temp.getAndIncrement()+"</p>\n" +
                    "            <p class=\"item\" style=\"text-align: center\">"+n.getKey()+"</p>\n" +
                    "            <p class=\"item\" style=\"text-align: right; margin-right: 10%\">"+(Integer.parseInt(n.getValue().toString())+1)+" Orders Placed</p>\n" +
                    "        </div>");
        });
        sb.append(l3);
        return sb.toString();
    }



    private static String l1= """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>subshop leaderboard</title>
            </head>
            <style>
                @import url('https://fonts.googleapis.com/css2?family=JetBrains+Mono:ital,wght@0,100..800;1,100..800&display=swap');
                body, html {
                    height: 100%;
                    width: 100%;
                    margin: 0;
                    font-family: "JetBrains Mono", monospace;
                    font-weight: 700;
                }
                .grid-container {
                    display: grid;
                    grid-template-columns: repeat(2, calc(100% / 2));
                    height: 91%;
                    width: 100%;
                }
                .leaderboard {
                    border: 1px solid #ccc;
                    padding-left: 10px;
                    text-align: left;
                    font-size: 18px;
                }
                .leaderboard-spot {
                    display: grid;
                    grid-template-columns: 10% 45% 45%;
                }
                .item {
            margin-top: 10px;
                    margin-bottom: 5px;
                font-size: x-large;
                }
            </style>
            <body>
            <div class="grid-container">
                <div id="calories" class="leaderboard">
                    <div style="font-size: xxx-large; font-weight: 700">calories leaderboard</div>
            """;

    private static String l2= """
                </div>
                <div id="orders" class="leaderboard">
                    <div style="font-size: xxx-large; font-weight: 700">orders leaderboard</div>
            """;

    private static String l3= """
                </div>
            </div>
<footer style="text-align: center; font-size:large; font-weight:600; padding:0"><a href="https://api.wduan.dev/v1/leaderboard/raw">raw data</a><br>wduan.dev<br>2024</footer>
           
            </body>
            </html>
            """;
}
