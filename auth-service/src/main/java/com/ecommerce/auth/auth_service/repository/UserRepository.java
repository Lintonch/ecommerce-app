package com.ecommerce.auth.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.auth.auth_service.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

	// Check if a user with the given username exists
	Boolean existsByUsername(String username);

	// Find a user by their email
	Optional<User> findByEmail(String email);

}
