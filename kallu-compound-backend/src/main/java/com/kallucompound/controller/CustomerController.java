package com.kallucompound.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kallucompound.dto.CustomerNameDTO;
import com.kallucompound.entity.Customer;
import com.kallucompound.repository.CustomerRepository;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(
            CustomerRepository customerRepository) {

        this.customerRepository =
                customerRepository;
    }

    // CUSTOMER REGISTRATION
    @PostMapping("/api/customers")
    public Customer registerCustomer(
            @RequestBody Customer customer) {

        return customerRepository.save(customer);
    }

    // ADMIN - GET ALL CUSTOMER DETAILS
    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    // PUBLIC - GET ONLY CUSTOMER ID AND NAME
    @GetMapping("/api/customers/names")
    public List<CustomerNameDTO> getCustomerNames() {

        return customerRepository.findAll()
                .stream()
                .map(customer ->
                    new CustomerNameDTO(
                        customer.getId(),
                        customer.getName()
                    )
                )
                .toList();
    }
}