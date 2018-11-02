package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Bike;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends PagingAndSortingRepository<Bike, Long> {
	Bike findByName(final String name);
	List<Bike> findByMakerNameIgnoreCase(final String makerName);
}
