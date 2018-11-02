package com.bf.bikefinder.dataloaders;

import org.springframework.scheduling.annotation.Scheduled;

public interface BikeDataLoader {

    @Scheduled(cron = "0 0 0 * * *")
        // Ejecutamos la recoleccion de datos todos los dias a las 00:00
    void collectData();

    void loadBikesBHVavr(String makerName);
}
