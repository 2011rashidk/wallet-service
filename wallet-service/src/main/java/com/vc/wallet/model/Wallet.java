package com.vc.wallet.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet")
public class Wallet extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long customerId;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Column(nullable = false, length = 10)
    private String currency = "INR";
}
