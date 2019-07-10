package com.example.HotelManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HotelManagement.model.JwtRefreshToken;

public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {

}
