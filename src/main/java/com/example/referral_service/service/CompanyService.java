package com.example.referral_service.service;

import com.example.referral_service.enitity.Company;
import com.example.referral_service.repositories.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public String getCompanyName(UUID companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new EntityNotFoundException("Company not found");
        }
        return company.getName();
    }

    public void addActiveRequest(UUID companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company == null) {
            throw new EntityNotFoundException("Company not found");
        }
        company.setActiveRequests(company.getActiveRequests() + 1);
        companyRepository.save(company);
    }

    public void addNewCompany(String companyName) {
        Company company = companyRepository.findByName(companyName).orElse(null);
        if (company == null) {
            company = Company.builder()
                    .name(companyName)
                    .activeRequests(0)
                    .build();
            companyRepository.save(company);
        }
    }
}
