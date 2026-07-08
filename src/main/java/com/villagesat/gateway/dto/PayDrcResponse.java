package com.villagesat.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record PayDrcResponse(
        boolean success,
        String status,
        @JsonProperty("provider_reference") String providerRef,
        @JsonProperty("error_message") String errorMessage
) {}