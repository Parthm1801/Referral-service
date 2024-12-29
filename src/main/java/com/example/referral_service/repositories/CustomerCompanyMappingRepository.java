package com.example.referral_service.repositories;

import com.example.referral_service.enitity.CustomerCompanyMappings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerCompanyMappingRepository extends JpaRepository<CustomerCompanyMappings, UUID> {
    public Optional<List<CustomerCompanyMappings>> findByCustomerId(UUID customerId);

    public Optional<List<CustomerCompanyMappings>> findByCompanyId(UUID companyId);
}
