package com.bf.bikefinder.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Maker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country; /* mejor que sea una tabla de countries fijos y esto solo un ID, pero habría que revisarlo bien (idiomas,...)*/
    private String description;
    private String urlDetails;

    private Maker() {
    }

    public Maker(final String name) {
        this.name = name;
    }
}
