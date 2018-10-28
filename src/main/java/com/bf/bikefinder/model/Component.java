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
    private Long typeId;
    private String makerId;
    private String description;
    private Integer year;
    private String urlDetails;
    private Float weight;
    private String urlImage;


    private Component() {
    }

    public Component(final String name, final Float price) {
        this.name = name;
        this.price = price;
    }

}
