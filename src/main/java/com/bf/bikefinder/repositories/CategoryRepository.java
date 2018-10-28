package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
