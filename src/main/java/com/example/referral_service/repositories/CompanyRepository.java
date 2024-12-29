package com.example.referral_service.repositories;

import com.example.referral_service.enitity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    public Optional<Company> findByName(String name);
}
