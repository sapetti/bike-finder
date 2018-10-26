package com.bf.bikefinder.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Bike {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private Float price;

    private Bike() {}

    public Bike(final String name, final Float price) {
        this.name = name;
        this.price = price;
    }

}
