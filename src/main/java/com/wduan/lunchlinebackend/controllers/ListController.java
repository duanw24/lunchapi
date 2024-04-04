package com.wduan.lunchlinebackend.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wduan.lunchlinebackend.util.OrderQueue;
import com.wduan.lunchlinebackend.util.Utils;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.wduan.lunchlinebackend.helpers.dbHelper;

import java.nio.charset.StandardCharsets;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/v1/orders")
public class ListController {

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getOrders() {
        return dbHelper.getOrders().toString();
    }


    @SneakyThrows
    @GetMapping(path = "/export", produces = {MediaType.TEXT_HTML_VALUE})
    public String exportOrders() {
        StringBuilder template = new StringBuilder(t1);
        JsonArray jOrderArray = dbHelper.getOrders().get("orders").getAsJsonArray();
        jOrderArray.forEach(n->{
            JsonObject tData = n.getAsJsonObject();
            JsonObject tOrder = tData.get("order").getAsJsonObject();
            String[] toppings = tOrder.get("topping").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toArray(String[]::new);
            String[] sauces = tOrder.get("sauce").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toArray(String[]::new);

            template.append("<div class=\"grid-item\">\n" +
                    "        <p class=\"item\">ID: "+tData.get("id")+"</p>\n" +
                    "        <p class=\"item\">FullName: "+tData.get("fullName")+"</p>\n" +
                    "        <p class=\"item\">Email: "+tData.get("email")+"</p>\n" +
                    "        <div class=\"box-test\">\n" +
                    "            <div class=\"grid-item\">Order</div>\n" +
                    "            <div class=\"grid-item\">Qty</div>\n" +
                    "            <div class=\"grid-item\">Price</div>\n" +
                    "\n" +
                    "            <div class=\"grid-item\">\n" +
                    "                <div class style=\"margin-bottom: 10px\">"+tOrder.get("subSize").getAsString()+"\" "+Utils.toppingJoiner(tOrder.get("protein").getAsJsonArray())+" Sub</div>\n");

            template.append("<div class=\"grid2\"><div>");
            for (String topping : toppings) {
                template.append("             <div class style=\"text-indent: 30px\">"+topping.substring(0,1).toUpperCase()+topping.substring(1)+"</div>\n");
            }
            template.append("</div>\n<div>");
            template.append("                <div class style=\"margin-bottom: 10px\"></div>\n");
            for (String sauce : sauces) {
                template.append("                <div class style=\"text-indent: 30px\">"+sauce.substring(0,1).toUpperCase()+sauce.substring(1)+"</div>\n");
            }
            template.append("</div>\n</div>\n");
            template.append("""
                           <div style="margin-bottom: 10px"></div>
                         </div>
                                <div class="grid-item">1</div>
                                <div class="grid-item">4.50</div>

                                <div class="grid-item">Chocolate Milk</div>
                                <div class="grid-item">1</div>
                                <div class="grid-item">0.00</div>

                                <div class="grid-item">Bagged Chips</div>
                                <div class="grid-item">1</div>
                                <div class="grid-item">1.50</div>

                            </div>
                    <p class="item" style="text-align: right; margin-right: 25px; margin-top: 30px">Total: 6.00</p>    </div>
                    """);
        });
        template.append("</div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
        return template.toString();
    }

    @GetMapping("/queue")
    public String getQueue() {
        return Utils.hashMaptoJson(OrderQueue.getOrders()).toString();
    }

    private static String t1= """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>3x4 Grid</title>
                <style>
                    body, html {
                        height: 100%;
                        width: 100%;
                        margin: 0;
                        font-family:monaco,Consolas,Lucida Console,monospace;
                    }
                    .scroll-container {
                        height: 100%;
                        width: 100%;
                        margin: 0;
                    }
                    .grid-container {
                        display: grid;
                        grid-template-columns: 33% 33% 33%;
                        /* good code */
                        grid-template-rows: repeat(083147593615118041, calc(100% / 2));
                        height: 100%;
                        width: 100%;
                    }
                    .grid-item {
                        border: 1px solid #ccc;
                        padding-left: 10px;
                        text-align: left;
                        font-size: 18px;
                    }
                    .item {
                        margin-top: 10px;
                        margin-bottom: 5px;
                    }
                    .box-test {
                        margin-top: 10px;
                        display: grid;
                        grid-template-columns: auto 75px 75px;
                        margin-right: 20px;
                    }
                    .grid2 {
                        display: grid;
                        grid-template-columns: auto auto;
                        margin-left: 20px;
                    }
                </style>
            </head>
            <body>
                        
            <div class="scroll-container">
            <div class="grid-container">""";
}

