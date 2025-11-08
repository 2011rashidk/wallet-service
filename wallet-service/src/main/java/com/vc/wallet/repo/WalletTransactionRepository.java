package com.vc.wallet.repo;

import com.vc.wallet.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
}
