package com.example.HotelManagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.HotelManagement.Response.ApiResponse;
import com.example.HotelManagement.model.Cart;

import com.example.HotelManagement.service.CartItemService;
import com.example.HotelManagement.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;

	@Autowired
	CartItemService cartItemService;

	@GetMapping("/getCartById")
	public ResponseEntity<?> getCartId() {
		try {
			Long cartId = cartService.getCartId();
			return new ResponseEntity<Object>(new ApiResponse(true, cartId, "get CartId Successfully"), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()), HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping("/getCart")
	public ResponseEntity<?> getCartItems(@Valid @RequestParam long cartId) {
		try {
			Cart cart = cartService.getCartById(cartId);
			return new ResponseEntity<Object>(new ApiResponse(true, cart, "get Cart Successfully"), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()), HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping("/add")
	public ResponseEntity<?> addCartItem(@Valid @RequestParam long productId) {
		try {
			cartItemService.addCartItem(productId);

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Add CartItem  successfull"), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteCartItem(@Valid @RequestParam long cartItem) {
		try {
			cartItemService.removeCartItem(cartItem);

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Delete CartItem  successfull"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deleteAll")
	public ResponseEntity<?> deleteAllCartItem() {
		try {
			cartItemService.removeAllCartItem();

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Delete Cart  successfull"), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
