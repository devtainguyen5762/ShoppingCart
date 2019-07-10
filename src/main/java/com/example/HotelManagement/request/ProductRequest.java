package com.example.HotelManagement.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductRequest {
	private Long id;

	private String productCategory;

	private String productDescription;

	private String productManufacturer;

	@NotEmpty(message = "Product Name is mandatory")

	private String productName;

	@NotNull(message = "Please provide some price")
	@Min(value = 100, message = "Minimum value should be greater than 100")

	private double productPrice;

	private String unitStock;

	private int warranty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
