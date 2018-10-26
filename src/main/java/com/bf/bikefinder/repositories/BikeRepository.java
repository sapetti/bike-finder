package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Bike;
import org.springframework.data.repository.CrudRepository;

public interface BikeRepository extends CrudRepository<Bike, Long> {
}
