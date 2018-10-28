package com.bf.bikefinder.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float rating; /*set as Float to make easier calculations on this field*/
    private String user; /* Id of the user?? Difficult to track when there is no user, External review for example. Needs more thinking*/
    private Integer weight;

    private Rating() {
    }

    public Rating(final Float rating) {
        this.rating = rating;
    }
}
