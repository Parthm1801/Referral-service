package com.example.referral_service.exception;

import com.example.referral_service.data.ReferralRequestStatus;

import java.util.UUID;

public class RequestExistsException extends RuntimeException {
    private String message;

    public RequestExistsException() {}

    public RequestExistsException(ReferralRequestStatus referralRequestStatus) {
        super();
        this.message = "Request already exists and in " + referralRequestStatus + " status";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
