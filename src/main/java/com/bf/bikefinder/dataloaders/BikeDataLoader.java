package com.bf.bikefinder.dataloaders;

import com.bf.bikefinder.model.Bike;
import com.bf.bikefinder.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BikeDataLoader implements CommandLineRunner {

    private final BikeRepository repository;

    @Autowired
    public BikeDataLoader(BikeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
//        this.repository.save(new Bike("T10", 1000f));
//        this.repository.save(new Bike("T11", 1000f));

        this.repository.findAll().forEach(System.out::println);
    }
}
