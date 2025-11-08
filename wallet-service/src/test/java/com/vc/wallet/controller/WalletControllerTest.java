package com.vc.wallet.controller;

import com.vc.wallet.service.WalletService;
import com.vc.wallet.web.WalletController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WalletControllerTest {

    private MockMvc mockMvc;
    private WalletService walletService;

    @BeforeEach
    void setup() {
        walletService = Mockito.mock(WalletService.class);
        WalletController controller = new WalletController(walletService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetBalance() throws Exception {
        Mockito.when(walletService.getBalance(1L)).thenReturn(500.0);

        mockMvc.perform(get("/api/wallets/1/balance"))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }

    @Test
    void testDebit() throws Exception {
        Mockito.when(walletService.debit(anyLong(), anyDouble(), anyString())).thenReturn(400.0);

        String json = """
            {
              "amount": 100.0,
              "referenceId": "REF-123"
            }
            """;

        mockMvc.perform(post("/api/wallets/1/debit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("400.0"));
    }

    @Test
    void testCredit() throws Exception {
        Mockito.when(walletService.credit(anyLong(), anyDouble(), anyString())).thenReturn(600.0);

        String json = """
            {
              "amount": 100.0,
              "referenceId": "REF-456"
            }
            """;

        mockMvc.perform(post("/api/wallets/1/credit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("600.0"));
    }
}

