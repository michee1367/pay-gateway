package com.villagesat.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record PayDrcRequest(
        @JsonProperty("merchant_id") String merchantId,
        @JsonProperty("merchant_secrete") String merchantSecret,
        String amount,
        String currency,
        String action, // "debit" ou "credit"
        @JsonProperty("customer_number") String customerNumber,
        String firstname,
        String lastname,
        @JsonProperty("e-mail") String email,
        String reference,
        String method, // "mpesa", "orange", "airtel"
        @JsonProperty("callback_url") String callbackUrl
) {}