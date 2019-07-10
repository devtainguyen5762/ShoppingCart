package com.example.HotelManagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.HotelManagement.model.Product;
import com.example.HotelManagement.request.ProductRequest;

@Service
public interface ProductService {

	List<Product> getAll();

	void addProduct(ProductRequest param);

	void editProduct(ProductRequest param);
	
	void deleteProduct(Long id);
}
