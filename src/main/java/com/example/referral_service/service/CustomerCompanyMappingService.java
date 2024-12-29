package com.example.referral_service.service;

import com.example.referral_service.enitity.CustomerCompanyMappings;
import com.example.referral_service.repositories.CustomerCompanyMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerCompanyMappingService {
    @Autowired
    private CustomerCompanyMappingRepository customerCompanyMappingRepository;

    public List<UUID> getAllCompaniesForCustomer(UUID customerId) {
        List<CustomerCompanyMappings> companiesMapping =  customerCompanyMappingRepository.findByCustomerId(customerId).orElse(List.of());
        return companiesMapping.stream().map(mapping -> mapping.companyId).toList();
    }
}
