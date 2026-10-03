package com.kallucompound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kallucompound.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);

}