package com.vc.wallet.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wallet_transaction")
public class WalletTransaction extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long walletId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false, length = 10)
    private String type; // DEBIT or CREDIT

    @Column(length = 100)
    private String referenceId;
}
