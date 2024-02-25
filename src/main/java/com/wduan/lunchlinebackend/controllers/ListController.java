package com.wduan.lunchlinebackend.controllers;

import org.bson.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wduan.lunchlinebackend.helpers.dbHelper;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/orders")
public class ListController {

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public String getOrders() {
        return dbHelper.getOrders().toString();
    }
}
