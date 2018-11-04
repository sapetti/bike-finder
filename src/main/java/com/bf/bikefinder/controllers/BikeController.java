package com.bf.bikefinder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bf.bikefinder.model.Bike;
import com.bf.bikefinder.repositories.BikeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bike")
public class BikeController {

	@Autowired
	private BikeRepository repo;

	@GetMapping
	public Page<Bike> listAll(Pageable p) {
		return repo.findAll(p);

	}

//    @GetMapping
//    public List<Bike> getBikesByName(@RequestParam(value = "compare", required = true) String names) {
//        return Arrays.stream(names.split(";"))
//                .map(repo::findByNameContaining)
//                .collect(Collectors.toList());
//
//    }
}
