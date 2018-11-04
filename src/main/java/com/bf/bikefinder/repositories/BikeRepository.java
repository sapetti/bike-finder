package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Bike;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends PagingAndSortingRepository<Bike, Long> {
	Bike findByName(final String name);
//    Optional<Bike> findByName(final String name);

	List<Bike> findByMakerNameIgnoreCase(final String makerName);

//    @Query("SELECT e FROM Employee e WHERE e.name LIKE %?1%")
//    List<Bike> findByNames(List<String> name);
//
//    List<Bike> findByNameContaining(String name);
//
//    List<Bike> findByIdIn(List<Long> ids);
}
