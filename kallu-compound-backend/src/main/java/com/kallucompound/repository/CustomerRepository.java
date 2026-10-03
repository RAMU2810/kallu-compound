package com.kallucompound.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kallucompound.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}