package com.iftm.prjreferencia.dto;

import java.io.Serializable;

import com.iftm.prjreferencia.entities.Product;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgURL;

	public ProductDTO() {

	}

	public ProductDTO(Long id, String name, String description, Double price, String imgURL) {		
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgURL = imgURL;
	}

	public ProductDTO(Product entity) {
		setId(entity.getId());
	    setName(entity.getName());
		setDescription(entity.getDescription());
		setPrice(entity.getPrice());
		setImgURL(entity.getImgURL());
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public Product toEntity() {
		return new Product(id, name, description, price, imgURL);
	}

}
