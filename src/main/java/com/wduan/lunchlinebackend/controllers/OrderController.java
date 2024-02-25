package com.wduan.lunchlinebackend.controllers;


import com.google.gson.Gson;
import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.OrderQueue;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import com.wduan.lunchlinebackend.helpers.emailHelper;
import com.wduan.lunchlinebackend.util.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"*"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    Gson gson = new Gson();

    @SneakyThrows
    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getPhoto2(HttpServletRequest request, HttpServletResponse response) {
        return new ClassPathResource("images/3d_arab.jpg").getContentAsByteArray();
    }


    @SneakyThrows
    @PostMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public Object submitOrder(HttpServletRequest request, HttpServletResponse response) {
        LogController.log("GET /api/v1/order from ip: " + request.getRemoteAddr());
        String queryString = java.net.URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        if(queryString!=null) {
            //actual cancer
            String[] params = queryString.split("&");
            String name = "";
            String email = "";
            int studentID = 0;
            int lunchPeriod = 0;
            String breadType = "";
            int subSize = 0;
            boolean toasted = false;
            String[] protein = new String[0];
            String[] toppings = new String[0];
            String[] sauces = new String[0];
            for (String param : params) {
                String[] pair = param.split("=");
                if (pair.length == 2) {
                    switch (pair[0]) {
                        case "fullName":
                            name = pair[1];
                            break;
                        case "email":
                            email = pair[1];
                            break;
                        case "studentID":
                            studentID = Integer.parseInt(pair[1]);
                            break;
                        case "lunchPeriod":
                            lunchPeriod = Integer.parseInt(pair[1]);
                            break;
                        case "breadType":
                            breadType = pair[1];
                            break;
                        case "subSize":
                            subSize = Integer.parseInt(pair[1]);
                            break;
                        case "toasted":
                            toasted = Boolean.parseBoolean(pair[1]);
                            break;
                        case "protein":
                            protein = pair[1].split(",");
                            break;
                        case "topping":
                            toppings = pair[1].split(",");
                            break;
                        case "sauce":
                            sauces = pair[1].split(",");
                            break;
                    }
                }
            }
            long timestamp=System.currentTimeMillis();
            Order order = new Order((dbHelper.getD0().countDocuments()+1)*timestamp,timestamp,name, email, studentID, lunchPeriod, breadType, subSize, toasted, protein, toppings, sauces);

            OrderQueue.addOrder(order);
            emailHelper.getInstance().sendConfirmation(order.getEmail(),order);
            //System.out.println(order);

            return "Order submitted";
        }
        return "#";
    }

}
