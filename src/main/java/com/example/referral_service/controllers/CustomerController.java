package com.example.referral_service.controllers;

import com.example.referral_service.data.SignupDetails;
import com.example.referral_service.enitity.Customer;
import com.example.referral_service.exception.CustomerExistsException;
import com.example.referral_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add-new")
    public ResponseEntity<?> addCustomer(@RequestBody SignupDetails details) {
        try {
            Customer customer =  customerService.addCustomer(details);
            return ResponseEntity.ok(customer);
        } catch (CustomerExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload-resume")
    public ResponseEntity<?> uploadResume(
            @RequestParam("file") MultipartFile file,
            @RequestHeader("Customer-ID") String customerId) {
        try {
            UUID cxId = UUID.fromString(customerId);
            customerService.uploadResume(cxId, file);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
