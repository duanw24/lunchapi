package com.wduan.lunchlinebackend.controllers;

import com.wduan.lunchlinebackend.util.OrderQueue;
import com.wduan.lunchlinebackend.util.Utils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.wduan.lunchlinebackend.helpers.dbHelper;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/api/v1/orders")
public class ListController {

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getOrders() {
        return dbHelper.getOrders().toString();
    }

    @GetMapping("/queue")
    public String getQueue() {
        return Utils.hashMaptoJson(OrderQueue.getOrders()).toString();
    }
}
