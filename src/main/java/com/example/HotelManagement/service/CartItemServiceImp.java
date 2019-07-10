package com.example.HotelManagement.service;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.HotelManagement.exception.BadRequestException;
import com.example.HotelManagement.model.Cart;
import com.example.HotelManagement.model.CartItem;
import com.example.HotelManagement.model.Customer;
import com.example.HotelManagement.model.Product;
import com.example.HotelManagement.model.User;
import com.example.HotelManagement.repository.CartItemRepository;
import com.example.HotelManagement.repository.CustomerRepository;
import com.example.HotelManagement.repository.ProductRepository;
import com.example.HotelManagement.repository.UserRepository;
import com.example.HotelManagement.security.UserPrincipal;

@Service
public class CartItemServiceImp implements CartItemService {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CartItemRepository cartItemRepository;

	@Override
	public void addCartItem(long ProductId) {
		// TODO Auto-generated method stub
		try {
			UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			long userId = userPrincipal.getId();
			Optional<User> foundUser = userRepository.findById(userId);
			User user = foundUser.get();
			long customerId = user.getCustomer().getCustomerId();
			Optional<Customer> foundCustomer = customerRepository.findById(customerId);
			Customer customer = foundCustomer.get();
			Cart cart = customer.getCart();
			List<CartItem> cartItems = cart.getCartItem();
			Optional<Product> foundProduct = productRepository.findById(ProductId);
			Product product = foundProduct.get();
			for (int i = 0; i < cartItems.size(); i++) {
				CartItem cartItem = cartItems.get(i);
				if (product.getProductId() == cartItem.getProduct().getProductId()) {
					cartItem.setQuality(cartItem.getQuality() + 1);
					cartItem.setPrice(cartItem.getQuality() * cartItem.getProduct().getProductPrice());
					cartItemRepository.save(cartItem);
					return;
				}
			}
			CartItem cartItem = new CartItem();
			cartItem.setQuality(1);
			cartItem.setProduct(product);
			cartItem.setPrice(product.getProductPrice() * 1);
			cartItem.setCart(cart);
			cartItemRepository.save(cartItem);
	

		} catch (Exception ex) {
			throw new BadRequestException("Can not add cart Item");
		}
	}

	@Override
	public void removeCartItem(long cartItemId) {
		// TODO Auto-generated method stub
		try {
			Optional<CartItem> foundCartItem = cartItemRepository.findById(cartItemId);
			if (!foundCartItem.isPresent()) {
				throw new BadRequestException("CartItem is not found");
			}
			CartItem cartItem = foundCartItem.get();
			Cart cart = cartItem.getCart();
			List<CartItem> cartItems = cart.getCartItem();
			cartItems.remove(cartItem);
			cartItemRepository.deleteById(cartItemId);
		} catch (Exception ex) {
			throw new BadRequestException("Can not delete cart Item");
		}
	}
	@Override
	public void removeAllCartItem() {
		// TODO Auto-generated method stub
		try {
			UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			long userId = userPrincipal.getId();
			Optional<User> foundUser = userRepository.findById(userId);
			User user = foundUser.get();
			long customerId = user.getCustomer().getCustomerId();
			Optional<Customer> foundCustomer = customerRepository.findById(customerId);
			Customer customer = foundCustomer.get();
			Cart cart = customer.getCart();
			List<CartItem> cartItems = cart.getCartItem();
//			for (CartItem cartItem : cartItems) {
//				cartItems.removeAll(cartItem)();
//			}
			cartItems.removeAll(cartItems);
			cartItemRepository.deleteAll(cartItems);
		} catch (Exception ex) {
			throw new BadRequestException("Can not delete cart Item");
		}
	}
}
