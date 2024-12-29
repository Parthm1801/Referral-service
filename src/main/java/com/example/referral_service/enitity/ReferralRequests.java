package com.example.referral_service.enitity;

import com.example.referral_service.data.ReferralRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "referral_requests")
public class ReferralRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    public Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "requestor_id")
    private UUID requestorId;

    @Column(name = "referrer_id")
    private UUID referrerId;

    @Column(name = "company_id")
    private UUID companyId;

    @Column
    private ReferralRequestStatus status;

    @PrePersist
    protected void onCreate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        this.createdAt = currentTimestamp;
        this.updatedAt = currentTimestamp;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
