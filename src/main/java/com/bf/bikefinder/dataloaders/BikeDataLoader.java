package com.bf.bikefinder.dataloaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bf.bikefinder.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

@Component
public class BikeDataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(BikeDataLoader.class);
    private final BikeRepository repository;

    @Autowired
    public BikeDataLoader(BikeRepository repository) {
        this.repository = repository;
    }

    @Scheduled(cron="0 0 0 * * *") // Ejecutamos la recoleccion de datos todos los dias a las 00:00
    public void collectData() {
        LOGGER.debug("collecting data...");
    }

}
