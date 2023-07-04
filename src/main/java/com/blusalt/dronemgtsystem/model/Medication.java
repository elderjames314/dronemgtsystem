package com.blusalt.dronemgtsystem.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blusalt.dronemgtsystem.exceptions.InvalidRequestException;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity(name = "medications")
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double weight;
    private String code;
    private String image;

    @ManyToMany(mappedBy = "medications")
    private List<Delivery> deliveries;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    public void setName(String name) {
        if (name.matches("^[a-zA-Z0-9\\-_]+$")) {
            this.name = name;
        } else {
            throw new InvalidRequestException("Invalid name. Only letters, numbers, '-', and '_' are allowed.");
        }
    }

    public void setCode(String code) {
        if (code.matches("^[A-Z_0-9]+$")) {
            this.code = code;
        } else {
            throw new InvalidRequestException("Invalid code. Only uppercase letters, underscore, and numbers are allowed.");
        }
    }


}
