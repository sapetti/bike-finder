package com.bf.bikefinder.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Rating {

    /*TODO
        This may be a Many to Many relantionshtip between component/bike and users. Check:
        https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float rating; /*set as Float to make easier calculations on this field*/
    private Long userId; /* Id of the user?? Difficult to track when there is no user, External review for example. Needs more thinking*/
    private Integer weight;
    @ManyToOne
    private Component component;

    private Rating() {
    }

    public Rating(final Float rating) {
        this.rating = rating;
    }
}
