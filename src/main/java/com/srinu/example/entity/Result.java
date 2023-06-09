package com.srinu.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "result")
@Entity
@Data
public class Result {

    @Id
    String name;

    String address;

    String city;

    String country;

    Integer pincode;

    Integer satScore;

    LocalDateTime createdTs;

    LocalDateTime updatedTs;

    @Enumerated(EnumType.STRING)
    ResultStatus resultStatus;

    @PrePersist
    public void prePersist() {
        this.createdTs = LocalDateTime.now();
        this.updatedTs = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTs = LocalDateTime.now();
    }

    public enum ResultStatus {
        PASS, FAIL
    }

}
