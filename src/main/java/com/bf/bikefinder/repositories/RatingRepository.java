package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
