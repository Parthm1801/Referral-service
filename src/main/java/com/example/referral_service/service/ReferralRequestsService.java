package com.example.referral_service.service;

import com.example.referral_service.data.CompanyWiseRequest;
import com.example.referral_service.data.NewReferralRequest;
import com.example.referral_service.data.ReferralRequestStatus;
import com.example.referral_service.data.RequestDetails;
import com.example.referral_service.enitity.Company;
import com.example.referral_service.enitity.Customer;
import com.example.referral_service.enitity.CustomerCompanyMappings;
import com.example.referral_service.enitity.ReferralRequests;
import com.example.referral_service.exception.RequestExistsException;
import com.example.referral_service.repositories.ReferralRequestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReferralRequestsService {
    @Autowired
    private ReferralRequestsRepository referralRequestsRepository;

    @Autowired
    CustomerCompanyMappingService customerCompanyMappingService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CustomerService customerService;

    public void addNewRequest(NewReferralRequest referralRequest) {
        List<ReferralRequests> existingPendingRequest =
                referralRequestsRepository.findByRequestorIdAndCompanyIdAndStatus(
                        referralRequest.referrerId,
                        referralRequest.companyId,
                        ReferralRequestStatus.CREATED
                ).orElse(List.of());
        if (!existingPendingRequest.isEmpty()) {
            throw new RequestExistsException(ReferralRequestStatus.CREATED);
        }

        ReferralRequests newRequest = ReferralRequests.builder()
                .companyId(referralRequest.companyId)
                .referrerId(referralRequest.referrerId)
                .status(ReferralRequestStatus.CREATED)
                .build();

        referralRequestsRepository.save(newRequest);
        // TODO: Send notifications about new request being created to all company employees
        // TODO: Add request count in the customer repository for keeping a count of the requests made
    }

    public List<CompanyWiseRequest> getAllPendingRequestsForUserId(UUID CustomerId) {
        List<UUID> companyIds = customerCompanyMappingService.getAllCompaniesForCustomer(CustomerId);
        List<CompanyWiseRequest> companyWiseRequestList = new ArrayList<>();

        companyIds.forEach(companyId -> {
            String companyName = companyService.getCompanyName(companyId);
            List<RequestDetails> requestDetailsList = new ArrayList<>();

            List<ReferralRequests> referralRequests =
                    referralRequestsRepository.findByStatusAndCompanyId(ReferralRequestStatus.CREATED, companyId)
                            .orElse(List.of());
            referralRequests.forEach(referralRequest -> {
                Customer customer = customerService.getCustomer(referralRequest.getRequestorId());
                RequestDetails requestDetails = RequestDetails.builder()
                        .requestId(referralRequest.id)
                        .requestStatus(referralRequest.getStatus())
                        .linkedId(customer.linkedInId)
                        .requestorName(customer.name)
                        .resumeId(customer.resumeId)
                        .build();
                requestDetailsList.add(requestDetails);
            });

            CompanyWiseRequest companyWiseRequest = CompanyWiseRequest.builder()
                    .company(companyName)
                    .requestDetailsList(requestDetailsList)
                    .build();
            companyWiseRequestList.add(companyWiseRequest);
        });

        return companyWiseRequestList;
    }


}
