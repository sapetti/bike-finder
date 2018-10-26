package com.bf.bikefinder.com.bf.bikefinder.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DataController {

    InputStream currentData;

    @PostMapping(value = "data", consumes = "text/csv")
    public String ingestData(@RequestBody InputStream body) {
        currentData = body;
        return "done!";
    }

    @GetMapping("data")
    public String getData() {
        return currentData != null ? currentData.toString() : "";
    }
}
