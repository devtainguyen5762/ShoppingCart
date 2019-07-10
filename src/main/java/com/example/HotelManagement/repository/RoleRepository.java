package com.example.HotelManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.HotelManagement.model.Role;
import com.example.HotelManagement.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName roleName);
}
