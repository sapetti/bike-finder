package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Rating;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RatingRepository extends PagingAndSortingRepository<Rating, Long> {
}
