package com.villagesat.gateway.dto;

import java.math.BigDecimal;

// Le JSON que ton application principale va envoyer à ce microservice
public record GatewayJobRequest(
        String phoneNumber,
        BigDecimal amount,
        String currency,
        String reference,
        String action, // "debit" (Retrait) ou "credit" (Dépôt)
        String provider // "mpesa", "orange", "airtel"
) {}