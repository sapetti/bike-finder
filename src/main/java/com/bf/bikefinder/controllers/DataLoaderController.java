package com.bf.bikefinder.controllers;

import com.bf.bikefinder.dataloaders.BikeDataLoaderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dataloader")
public class DataLoaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoaderController.class);

    @Autowired
    private BikeDataLoaderImpl dataLoader;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loadData(@RequestParam(value = "java8", defaultValue = "false") Boolean isJava8) {
        LOGGER.debug("loadData");
        dataLoader.collectData();
        return "done!";
    }

    @GetMapping(value = "java8", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loadData(@RequestParam(value = "maker", defaultValue = "BH") String makerName) {
        LOGGER.debug("loadData::Java8");
        dataLoader.loadBikesBHVavr(makerName);
        return "{\"message\": \"done!\"}";
    }
}
