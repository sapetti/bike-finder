package com.bf.bikefinder.repositories;

import com.bf.bikefinder.model.User;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	User findByUsername(String username);

}
