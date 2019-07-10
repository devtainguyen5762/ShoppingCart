package com.example.HotelManagement.service;

import org.springframework.stereotype.Service;

import com.example.HotelManagement.model.Cart;

@Service
public interface CartService {

	long getCartId();

	Cart getCartById(long cartId);
}
