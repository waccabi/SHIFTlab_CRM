package com.ruf.shiftlab_crm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "seller")
public class Seller {
    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private String contactInfo;

    @Column(nullable = false)
    private LocalDateTime registrationDate;
}
