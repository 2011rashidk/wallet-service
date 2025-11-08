package com.vc.wallet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AmountRequest {
    @NotNull
    @Min(0)
    private Double amount;

    private String referenceId;
}
