package com.spoton.esm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spoton.esm.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
