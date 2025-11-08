package com.vc.wallet.service;

import com.vc.wallet.model.Wallet;
import com.vc.wallet.model.WalletTransaction;
import com.vc.wallet.repo.WalletRepository;
import com.vc.wallet.repo.WalletTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletService {

    private final WalletRepository wallets;
    private final WalletTransactionRepository transactions;

    public WalletService(WalletRepository wallets, WalletTransactionRepository transactions) {
        this.wallets = wallets;
        this.transactions = transactions;
    }

    @Transactional(readOnly = true)
    public Double getBalance(Long walletId) {
        Wallet w = wallets.findById(walletId).orElseThrow(() -> new IllegalArgumentException("Wallet not found: " + walletId));
        return w.getBalance();
    }

    @Transactional
    public Double debit(Long walletId, double amount, String referenceId) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        Wallet w = wallets.findById(walletId).orElseThrow(() -> new IllegalArgumentException("Wallet not found: " + walletId));
        if (w.getBalance() < amount) throw new IllegalStateException("Insufficient balance");
        w.setBalance(w.getBalance() - amount);
        wallets.save(w);
        var tx = WalletTransaction.builder()
                .walletId(walletId)
                .amount(amount)
                .type("DEBIT")
                .referenceId(referenceId)
                .build();
        transactions.save(tx);
        return w.getBalance();
    }

    @Transactional
    public Double credit(Long walletId, double amount, String referenceId) {
        Wallet w = wallets.findById(walletId).orElseThrow(() -> new IllegalArgumentException("Wallet not found: " + walletId));
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        w.setBalance(w.getBalance() + amount);
        wallets.save(w);
        var tx = WalletTransaction.builder()
                .walletId(walletId)
                .amount(amount)
                .type("CREDIT")
                .referenceId(referenceId)
                .build();
        transactions.save(tx);
        return w.getBalance();
    }
}
