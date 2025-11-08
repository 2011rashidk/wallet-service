package com.vc.wallet.service;

import com.vc.wallet.model.Wallet;
import com.vc.wallet.model.WalletTransaction;
import com.vc.wallet.repo.WalletRepository;
import com.vc.wallet.repo.WalletTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceTest {

    private WalletRepository walletRepo;
    private WalletTransactionRepository txRepo;
    private WalletService service;

    private Wallet wallet;

    @BeforeEach
    void setup() {
        walletRepo = mock(WalletRepository.class);
        txRepo = mock(WalletTransactionRepository.class);
        service = new WalletService(walletRepo, txRepo);

        wallet = Wallet.builder()
                .id(1L)
                .customerId(100L)
                .balance(500.0)
                .currency("INR")
                .build();
    }

    @Test
    void getBalance_success() {
        when(walletRepo.findById(1L)).thenReturn(Optional.of(wallet));

        Double balance = service.getBalance(1L);

        assertEquals(500.0, balance);
        verify(walletRepo).findById(1L);
    }

    @Test
    void getBalance_walletNotFound() {
        when(walletRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.getBalance(1L));
    }

    @Test
    void debit_success() {
        when(walletRepo.findById(1L)).thenReturn(Optional.of(wallet));
        when(walletRepo.save(any(Wallet.class))).thenAnswer(inv -> inv.getArgument(0));
        when(txRepo.save(any(WalletTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Double newBalance = service.debit(1L, 100.0, "REF-1");

        assertEquals(400.0, newBalance);
        verify(walletRepo).save(wallet);
        verify(txRepo).save(any(WalletTransaction.class));
    }

    @Test
    void debit_insufficientBalance() {
        wallet.setBalance(50.0);
        when(walletRepo.findById(1L)).thenReturn(Optional.of(wallet));

        assertThrows(IllegalStateException.class, () -> service.debit(1L, 100.0, "REF-2"));
    }

    @Test
    void debit_negativeAmount() {
        when(walletRepo.findById(1L)).thenReturn(Optional.of(wallet));

        assertThrows(IllegalArgumentException.class, () -> service.debit(1L, -10.0, "REF-3"));
    }

    @Test
    void credit_success() {
        when(walletRepo.findById(1L)).thenReturn(Optional.of(wallet));
        when(walletRepo.save(any(Wallet.class))).thenAnswer(inv -> inv.getArgument(0));
        when(txRepo.save(any(WalletTransaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Double newBalance = service.credit(1L, 200.0, "REF-4");

        assertEquals(700.0, newBalance);
        verify(walletRepo).save(wallet);
        verify(txRepo).save(any(WalletTransaction.class));
    }

    @Test
    void credit_negativeAmount() {
        when(walletRepo.findById(1L)).thenReturn(Optional.of(wallet));

        assertThrows(IllegalArgumentException.class, () -> service.credit(1L, -50.0, "REF-5"));
    }
}

