package com.example.referral_service.repositories;

import com.example.referral_service.enitity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    public Optional<Customer> findByLinkedInId(String linkedinId);

    public Optional<Customer> findByEmailId(String email);
}
