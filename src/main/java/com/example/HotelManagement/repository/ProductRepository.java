package com.example.HotelManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.HotelManagement.model.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	  @Query("FROM Product p WHERE p.productName= :Name")
	  List<Product> getAllProductByName(@Param("Name")String Name);
	  List<Product> findAll();
	  
	  Optional<Product>  findById(long id);

}
