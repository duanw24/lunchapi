package com.wduan.lunchlinebackend.controllers;

import org.bson.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.wduan.lunchlinebackend.helpers.dbHelper;

import java.util.ArrayList;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev"},
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
}
