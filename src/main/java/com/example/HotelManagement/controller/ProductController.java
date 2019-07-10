package com.example.HotelManagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HotelManagement.Response.ApiResponse;
import com.example.HotelManagement.model.Product;
import com.example.HotelManagement.request.ProductRequest;
import com.example.HotelManagement.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		try {
			List<Product> listProduct = productService.getAll();

			return new ResponseEntity<Object>(new ApiResponse(true, listProduct, "Get All Product successfull"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return null;
		}
	}

	@PostMapping("/add-product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
		try {
			productService.addProduct(productRequest);

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Add New Product successfull"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/edit-product")
	public ResponseEntity<?> editProduct(@Valid @RequestBody ProductRequest productRequest) {
		try {
			productService.editProduct(productRequest);

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Edit  Product successful"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
	

	@DeleteMapping("/delete-product")
	public ResponseEntity<?> deleteProduct(@Valid @RequestParam long id) {
		try {
			productService.deleteProduct(id);

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Delete  Product successful"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}
}
