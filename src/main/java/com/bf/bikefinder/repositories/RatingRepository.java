package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
}
