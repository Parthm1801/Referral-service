package com.example.referral_service.data;

import lombok.Builder;

import java.util.List;

@Builder
public class CompanyWiseRequest {
    public String company;
    public List<RequestDetails> requestDetailsList;
}

