package com.example.referral_service.controllers;

import com.example.referral_service.data.NewReferralRequest;
import com.example.referral_service.exception.RequestExistsException;
import com.example.referral_service.service.ReferralRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
public class ReferralRequestController {
    @Autowired
    ReferralRequestsService referralRequestsService;

    @PostMapping("/add-request")
    public ResponseEntity<?> addNewReferralRequest(@RequestBody NewReferralRequest referralRequest) {
        try {
            referralRequestsService.addNewRequest(referralRequest);
            return ResponseEntity.ok().build();
        } catch (RequestExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
