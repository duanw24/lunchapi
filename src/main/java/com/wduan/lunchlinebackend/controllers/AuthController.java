package com.wduan.lunchlinebackend.controllers;

import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.util.OrderQueue;
import com.wduan.lunchlinebackend.helpers.dbHelper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.apache.coyote.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @SneakyThrows
    @GetMapping(produces = {MediaType.TEXT_HTML_VALUE})
    public ResponseEntity<Object> confirmOrder(HttpServletRequest request) {
        //literal cancer
        LogController.log("GET /api/v1/auth from ip: " + request.getRemoteAddr());
        String queryString = java.net.URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        if(queryString!=null) {
            String id=queryString.split("=")[1];
            if(OrderQueue.getOrders().containsKey(id)) {
                dbHelper.submitOrder(OrderQueue.getOrder(id));
                OrderQueue.getOrders().remove(id);
                //LogController.log("Order id= " + id + " submitted!");
                return ResponseEntity.ok(new ClassPathResource("static/lunchapp/success.html"));
            } else {
                LogController.log("Order id= " + id + " not found!");
                return ResponseEntity.ok(new ClassPathResource("static/lunchapp/success.html"));
            }
        }
        LogController.log("No Key Provided");
        return ResponseEntity.ok(new ClassPathResource("static/emotiguy/3d_arab2.png").getContentAsByteArray());
    }
}
