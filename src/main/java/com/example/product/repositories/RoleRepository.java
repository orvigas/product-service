package com.example.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.product.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
