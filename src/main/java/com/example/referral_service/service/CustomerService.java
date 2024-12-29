package com.example.referral_service.service;

import com.example.referral_service.data.SignupDetails;
import com.example.referral_service.enitity.Customer;
import com.example.referral_service.exception.CustomerExistsException;
import com.example.referral_service.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    FileIOService fileIOService;

    public Customer addCustomer(SignupDetails details) {
        Optional<Customer> existingCustomer = customerRepository.findByEmailId(details.emailId);
        if (existingCustomer.isPresent()) {
            throw new CustomerExistsException("Customer with LinkedIn ID already exists");
        }

        Customer newCustomer = Customer.builder()
                .name(details.name)
                .emailId(details.emailId)
                .build();
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    public Customer getCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new EntityNotFoundException("Customer with $customerId not found");
        }
        return customer;
    }

    public void uploadResume(UUID customerId, MultipartFile resumeFile) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new EntityNotFoundException("Customer with $customerId not found");
        }
        UUID resumeId = UUID.randomUUID();
        fileIOService.uploadFile(resumeId.toString(), resumeFile)
                        .thenApply(success -> {
                            if (success) {
                                customer.setResumeId(resumeId);
                                customerRepository.save(customer);
                            } else {
                                // TODO: Throw Exception
                                System.out.println("File upload failed.");
                            }
                            return success;
                        })
                .exceptionally(e -> {
                    System.err.println("Error during file upload: " + e.getMessage());
                    return false;
                });
    }

    // TODO: remove
    public void deleteCustomer(String linkedinId) {
        Optional<Customer> existingCustomer = customerRepository.findByLinkedInId(linkedinId);
        customerRepository.delete(existingCustomer.get());
    }
}
