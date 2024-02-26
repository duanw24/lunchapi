package com.wduan.lunchlinebackend;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import com.wduan.lunchlinebackend.helpers.dbHelper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bson.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.File;



@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","lunchapi.wduan.dev"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/*")
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class LunchlineBackendApplication {
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(LunchlineBackendApplication.class, args);
       dbHelper.init();
    }

    @GetMapping()
    public File home() {
        return new File("resources/images/3d_arab.jpg");
    }
}
