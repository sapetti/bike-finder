package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.Component;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ComponentRepository extends PagingAndSortingRepository<Component, Long> {
}
