package com.example.referral_service.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer_company_mappings")
public class CustomerCompanyMappings {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "company_id")
    public UUID companyId;

    @Column(name = "customer_id")
    public UUID customerId;

    @Column(name = "is_current")
    public Boolean isCurrent;
}
