package com.wduan.lunchlinebackend;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/logs")

public class LogController {

    @Getter
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

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
