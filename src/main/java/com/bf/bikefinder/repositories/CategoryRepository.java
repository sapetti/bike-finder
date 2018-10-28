package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}
