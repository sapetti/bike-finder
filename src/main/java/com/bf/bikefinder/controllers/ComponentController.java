package com.bf.bikefinder.controllers;

import com.bf.bikefinder.model.Component;
import com.bf.bikefinder.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/component")
public class ComponentController {

    @Autowired
    private ComponentRepository repo;

    @GetMapping
    public Page<Component> listAll(Pageable p) {
        return repo.findAll(p);

    }
}
