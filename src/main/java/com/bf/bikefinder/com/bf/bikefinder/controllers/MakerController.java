package com.bf.bikefinder.com.bf.bikefinder.controllers;

import com.bf.bikefinder.model.Maker;
import com.bf.bikefinder.repositories.MakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/maker")
public class MakerController {

    @Autowired
    private MakerRepository repo;

    @GetMapping
    public Page<Maker> listAll(Pageable p) {
        return repo.findAll(p);

    }
}
