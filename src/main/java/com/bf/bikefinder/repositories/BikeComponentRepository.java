package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.BikeComponent;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BikeComponentRepository extends PagingAndSortingRepository<BikeComponent, Long> {
	BikeComponent findByName(String name);

	BikeComponent findByNameAndType(String text, String text2);
}
