package com.example.referral_service.repositories;

import com.example.referral_service.data.ReferralRequestStatus;
import com.example.referral_service.enitity.ReferralRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReferralRequestsRepository extends JpaRepository<ReferralRequests, UUID> {
    public Optional<List<ReferralRequests>> findByRequestorIdAndStatus(UUID referrerId, ReferralRequestStatus status);
    public Optional<List<ReferralRequests>> findByRequestorIdAndCompanyIdAndStatus(UUID referrerId, UUID companyId, ReferralRequestStatus status);
    public Optional<List<ReferralRequests>> findByStatusAndCompanyId(ReferralRequestStatus status, UUID companyId);
    public Optional<List<ReferralRequests>> findByReferrerIdAndStatus(UUID ReferrerId, ReferralRequestStatus status);
}
