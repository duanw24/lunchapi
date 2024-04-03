package com.wduan.lunchlinebackend;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.stream.Collectors;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost","https://lunchapp.wduan.dev","http://localhost:63342"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST}
)
@RestController
@RequestMapping("/v1/logs")
public class LogController {

    @Getter
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    @SneakyThrows
    public static void init() {
        BufferedWriter bfw = new BufferedWriter(new FileWriter("logs/log.log",false));
        bfw.write("");
        logger.info("LogController initialized");
    }

    public static void clearLog() {}

    public static void log(String message) {
        logger.info(message);
    }

    @GetMapping(produces = "text/plain")
    public String getLogs(HttpServletRequest request) {
        log("GET /api/v1/logs from ip: " + request.getRemoteAddr());
        try {
            // Read logs from the log file (adjust the path as needed)
            String logs = new BufferedReader(new FileReader("logs/log.log"))
                    .lines()
                    .collect(Collectors.joining("\n"));
            return logs;
        } catch (IOException e) {
            logger.error("Error reading logs", e);
            return "Error reading logs";
        }
    }

}
