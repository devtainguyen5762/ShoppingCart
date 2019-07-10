package com.example.HotelManagement.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Products")
public class Product implements Serializable {
	private static final long serialVersionUID = 5186013952828648626L;
	
	public Product(long productId, String productCategory, String productDescription, String productManufacturer,
			String productName, double productPrice, String unitStock,int warranty) {
		super();
		this.id = productId;
		this.productCategory = productCategory;
		this.productDescription = productDescription;
		this.productManufacturer = productManufacturer;
		this.productName = productName;
		this.productPrice = productPrice;
		this.unitStock = unitStock;
		this.warranty = warranty;
	}

	public Product() {

	}
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "category")
	private String productCategory;

	@Column(name = "description")
	private String productDescription;

	@Column(name = "manufacturer")
	private String productManufacturer;

	@NotEmpty(message = "Product Name is mandatory")
	@Column(name = "name")
	private String productName;

	@NotNull(message = "Please provide some price")
	@Min(value = 100, message = "Minimum value should be greater than 100")
	@Column(name = "price")
	private double productPrice;

	@Column(name = "unit")
	private String unitStock;

	@Column(name = "warranty")
	private int warranty;

	public long getProductId() {
		return id;
	}

	public void setProductId(long productId) {
		this.id = productId;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductManufacturer() {
		return productManufacturer;
	}

	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getUnitStock() {
		return unitStock;
	}

	public void setUnitStock(String unitStock) {
		this.unitStock = unitStock;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}



}
