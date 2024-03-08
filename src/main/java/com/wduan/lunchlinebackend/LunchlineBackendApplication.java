package com.wduan.lunchlinebackend;

import com.wduan.lunchlinebackend.helpers.dbHelper;
import com.wduan.lunchlinebackend.util.Utils;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public Object home() {
        return new ClassPathResource("emotiguy/"+Utils.randomEmotiGuy());
    }
}
