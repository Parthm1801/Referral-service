package com.example.referral_service.data;

import lombok.Builder;

import java.util.UUID;

@Builder
public class RequestDetails {
    public UUID requestId;
    public String requestorName;
    public String linkedId;
    public UUID resumeId;
    public ReferralRequestStatus requestStatus;
}
