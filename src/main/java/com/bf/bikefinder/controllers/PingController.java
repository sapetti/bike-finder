package com.bf.bikefinder.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PingController {
    @GetMapping("ping")
    public Map<String, String> ping() {
        Map<String, String> response = new HashMap<>();
        response.put("pong", "Hello World!");
        return response;
    }
    
    @GetMapping("admin/ping")
    public Map<String, String> pingAdmin() {
        Map<String, String> response = new HashMap<>();
        response.put("pong", "Hello Admin!");
        return response;
    }
}
