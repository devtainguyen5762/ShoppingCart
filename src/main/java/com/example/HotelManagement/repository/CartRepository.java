package com.example.HotelManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HotelManagement.model.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findById(long cartId);
}
