package com.movieBooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movieBooking.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRoleName(String string);

}
