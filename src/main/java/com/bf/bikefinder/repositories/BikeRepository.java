package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Bike;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BikeRepository extends PagingAndSortingRepository<Bike, Long> {
	Bike findByName(String name);
}
