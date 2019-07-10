package com.example.HotelManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.HotelManagement.model.Cart;
import com.example.HotelManagement.model.CartItem;
import com.example.HotelManagement.model.Customer;
import com.example.HotelManagement.model.User;
import com.example.HotelManagement.repository.CartRepository;
import com.example.HotelManagement.repository.CustomerRepository;
import com.example.HotelManagement.repository.UserRepository;

import com.example.HotelManagement.security.UserPrincipal;

@Service
public class CartServiceImp implements CartService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CartRepository cartRepository;

	@Override
	public long getCartId() {
		try {
			UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			long userId = userPrincipal.getId();
			Optional<User> foundUser = userRepository.findById(userId);
			User user = foundUser.get();
			long customerId = user.getCustomer().getCustomerId();
			Optional<Customer> foundCustomer = customerRepository.findById(customerId);
			Customer customer = foundCustomer.get();

			return customer.getCart().getCartId();

		} catch (Exception ex) {
			return 0;
		}

	}

	@Override
	public Cart getCartById(long cartId) {
		try {
			double grandTotal = 0;
			Optional<Cart> foundCart = cartRepository.findById(cartId);
			Cart cart = foundCart.get();

			List<CartItem> cartItems = cart.getCartItem();
			for (CartItem item : cartItems) {
				grandTotal += item.getPrice();
			}
			
			cart.setTotalPrice(grandTotal);
			return cart;
		} catch (Exception ex) {
			return null;
		}
	}

}
