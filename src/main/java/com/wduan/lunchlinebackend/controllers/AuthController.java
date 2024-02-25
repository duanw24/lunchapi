package com.wduan.lunchlinebackend.controllers;

import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.OrderQueue;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import com.wduan.lunchlinebackend.util.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"*"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @SneakyThrows
    @GetMapping(produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public Object confirmOrder(HttpServletRequest request, HttpServletResponse response) {
        //literal cancer
        LogController.log("GET /api/v1/auth from ip: " + request.getRemoteAddr());
        String queryString = java.net.URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        if(queryString!=null) {
            String id=queryString.split("=")[1];
            if(OrderQueue.getOrders().containsKey(id)) {
                dbHelper.submitOrder(OrderQueue.getOrder(id));
                OrderQueue.getOrders().remove(id);
                //LogController.log("Order id= " + id + " submitted!");
                return new ClassPathResource("images/3d_cool.jpg").getContentAsByteArray();
            } else {
                LogController.log("Order id= " + id + " not found!");
                return new ClassPathResource("images/3d_arab2.png").getContentAsByteArray();
            }
        }
        LogController.log("No Key Provided");
        return new ClassPathResource("images/3d_arab2.png").getContentAsByteArray();
    }
}
