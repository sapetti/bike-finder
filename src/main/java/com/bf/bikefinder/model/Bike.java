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
    private String size; /* Revisar si este campo se pilla de base de datos (preferido) o es una enum de constantes */
    private String description;
    private Integer year;
    private String urlDetails;
    private Long makerId;
    private Long categoryId;
    private Float weight;
    private String urlImage;


    private Bike() {}

    public Bike(final String name, final Float price) {
        this.name = name;
        this.price = price;
    }

}
