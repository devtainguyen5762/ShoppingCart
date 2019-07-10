package com.example.HotelManagement.service;

import org.springframework.stereotype.Service;

@Service
public interface CartItemService {

	void addCartItem(long ProductId);
	void removeCartItem(long CartItem);
	void removeAllCartItem();
}
