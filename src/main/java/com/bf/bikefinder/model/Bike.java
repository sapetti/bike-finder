package com.bf.bikefinder.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Bike {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id;
    private String name;
    private double price;
    private String size; /* Revisar si este campo se pilla de base de datos (preferido) o es una enum de constantes */
    private String description;
    private Integer year;
    private String urlDetails;
    @ManyToOne
    private Maker maker;
    private Long categoryId;
    private String category;
    private Float weight;
    private String urlImage;


    @ManyToMany
    private List<BikeComponent> componentList;


    private Bike() {}

    public Bike(final String name) {
        this.name = name;
      
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getUrlDetails() {
		return urlDetails;
	}

	public void setUrlDetails(String urlDetails) {
		this.urlDetails = urlDetails;
	}

    public Maker getMaker() {
        return maker;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

    public List<BikeComponent> getComponentList() {
        return componentList;
    }

    public void setComponentList(List<BikeComponent> componentList) {
        this.componentList = componentList;
    }

}
