package com.wduan.lunchlinebackend.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/refresh")

public class SyncController {
    @GetMapping(produces = {MediaType.ALL_VALUE})
    public Object refresh() {
return 1;
    }
}
