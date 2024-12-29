package com.example.referral_service.enitity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    public Timestamp createdAt;

    public String name;

    @Column(name = "email_id")
    public String emailId;

    @Column(name = "linkedin_id")
    public String linkedInId;

    @Column(name = "resume_id")
    public UUID resumeId;

    @Column(name = "profile_image_id")
    public UUID profileImageId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
