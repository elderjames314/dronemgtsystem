package com.blusalt.dronemgtsystem.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blusalt.dronemgtsystem.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @Column(name = "delivery_location")
    private String deliveryLocation;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryAudit> audits = new ArrayList<>();

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
