package com.villagesat.gateway.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

// Le JSON que ton application principale va envoyer à ce microservice
public record GatewayJobRequest(
        @JsonProperty("phone_number") String phoneNumber,
        BigDecimal amount,
        String currency,
        String reference,
        String action, // "debit" (Retrait) ou "credit" (Dépôt)
        String provider, // "mpesa", "orange", "airtel"
        @JsonProperty("callback_url") String callbackUrl
) {}