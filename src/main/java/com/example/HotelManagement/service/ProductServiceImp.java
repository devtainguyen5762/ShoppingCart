package com.example.HotelManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HotelManagement.exception.BadRequestException;
import com.example.HotelManagement.model.Product;
import com.example.HotelManagement.repository.ProductRepository;
import com.example.HotelManagement.request.ProductRequest;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> getAll() {
		return productRepository.findAll();
	}

	@Override
	public void addProduct(ProductRequest param) {
		// TODO Auto-generated method stub
		try {
			Product newProduct = new Product();
			List<Product> existProduct = productRepository.getAllProductByName(param.getProductName());
			if (existProduct != null && existProduct.size() > 0) {

				throw new BadRequestException("Product Name exist");
			}
			newProduct.setProductName(param.getProductName());
			newProduct.setProductCategory(param.getProductCategory());
			newProduct.setProductDescription(param.getProductDescription());
			newProduct.setProductManufacturer(param.getProductManufacturer());
			newProduct.setProductPrice(param.getProductPrice());
			newProduct.setUnitStock(param.getUnitStock());
			newProduct.setWarranty(param.getWarranty());
			existProduct.add(newProduct);
			productRepository.save(newProduct);
		} catch (BadRequestException ex) {

			throw ex;
		}
	}

	@Override
	public void editProduct(ProductRequest param) {
		// TODO Auto-generated method stub
		try {
			Optional<Product> foundProduct  = productRepository.findById(param.getId());
			
			if (!foundProduct.isPresent()) {
				throw new BadRequestException("Product is not found");
			}
			Product newProduct = foundProduct.get();
			newProduct.setProductName(param.getProductName());
			newProduct.setProductCategory(param.getProductCategory());
			newProduct.setProductDescription(param.getProductDescription());
			newProduct.setProductManufacturer(param.getProductManufacturer());
			newProduct.setProductPrice(param.getProductPrice());
			newProduct.setUnitStock(param.getUnitStock());
			newProduct.setWarranty(param.getWarranty());
			productRepository.save(newProduct);
		} catch (BadRequestException ex) {

			throw ex;
		}
	}

	@Override
	public void deleteProduct(Long id) {
		// TODO Auto-generated method stub
		try {
			Optional<Product> foundProduct  = productRepository.findById(id);
			
			if (!foundProduct.isPresent()) {
				throw new BadRequestException("Product is not found");
			}
			
			productRepository.deleteById(id);
		} catch (BadRequestException ex) {

			throw ex;
		}
	}

}
