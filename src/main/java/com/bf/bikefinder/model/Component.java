package com.bf.bikefinder.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Float price;
    private Long type_id;
    private String maker_id;
    private String description;
    private Integer year;
    private String url_details;
    private Float weight;
    private String url_image;


    private Component() {
    }

    public Component(final String name, final Float price) {
        this.name = name;
        this.price = price;
    }

}
