package com.vc.wallet.web;

import com.vc.wallet.dto.AmountRequest;
import com.vc.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService service;

    public WalletController(WalletService service) {
        this.service = service;
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<Double> balance(@PathVariable Long walletId) {
        return ResponseEntity.ok(service.getBalance(walletId));
    }

    @PostMapping("/{walletId}/debit")
    public ResponseEntity<Double> debit(@PathVariable Long walletId, @Validated @RequestBody AmountRequest req) {
        return ResponseEntity.ok(service.debit(walletId, req.getAmount(), req.getReferenceId()));
    }

    @PostMapping("/{walletId}/credit")
    public ResponseEntity<Double> credit(@PathVariable Long walletId, @Validated @RequestBody AmountRequest req) {
        return ResponseEntity.ok(service.credit(walletId, req.getAmount(), req.getReferenceId()));
    }
}
