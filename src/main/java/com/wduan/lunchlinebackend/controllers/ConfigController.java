package com.wduan.lunchlinebackend.controllers;

import com.wduan.lunchlinebackend.LogController;
import com.wduan.lunchlinebackend.Config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/v1/secret/config")
public class ConfigController {

    @RequestMapping(produces = "application/json")
    public String change(HttpServletRequest request, HttpServletResponse response) {
        LogController.log("CONFIG CHANGE REQUESTED from ip: " + request.getRemoteAddr());
        String queryString = java.net.URLDecoder.decode(request.getQueryString(), StandardCharsets.UTF_8);
        if(queryString!=null) {
            String[] params=queryString.split("&");
            Arrays.stream(params).forEach(p->{
                String[] kv = p.split("=");
                switch(kv[0]) {
                    case "emailAuth":
                        boolean temp;
                        try {
                           temp = Boolean.parseBoolean(kv[1]);
                        } catch(Exception e) {
                            LogController.log("Invalid value for emailAuth, requires BOOLEAN: " + kv[1]);
                            try {
                                response.sendError(400, "Invalid value for emailAuth, requires BOOLEAN: " + kv[1]);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            return;
                        }
                        Config.setEmailAuth(temp);
                        LogController.log("EmailAuth set to " + kv[1]);
                        break;
                    case "queueSize":
                        int si;
                        try {
                            si = Integer.parseInt(kv[1]);
                        } catch(Exception e) {
                            LogController.log("Invalid value for queueSize, requires INTEGER: " + kv[1]);
                            try {
                                response.sendError(400, "Invalid value for queueSize, requires INTEGER: " + kv[1]);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            return;
                        }
                        Config.setMaxOrders(si);
                        LogController.log("Queue set to " + kv[1]);
                        break;
                    default:
                        LogController.log("Unknown setting " + kv[0] + " change requested to " + kv[1]);
                        try {
                            response.sendError(400, "Unknown setting " + kv[0] + " change requested to " + kv[1]);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                }
            });
        }
        return "OK";
    }
}
