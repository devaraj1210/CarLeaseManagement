package com.trimblecars.repository;

import com.trimblecars.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
	Users findByEmail(String email);

}

