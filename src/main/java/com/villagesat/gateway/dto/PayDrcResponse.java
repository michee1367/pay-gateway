package com.villagesat.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PayDrcResponse(
        // On mappe "Status" (Success, Failed, Pending, etc.) pour déterminer le succès métier
        @JsonProperty("Status") String status,
        
        @JsonProperty("Amount") Double amount,
        @JsonProperty("Comment") String comment,
        @JsonProperty("Currency") String currency,
        @JsonProperty("Customer_Number") String customerNumber,
        @JsonProperty("Reference") String reference,
        @JsonProperty("Transaction_id") String transactionId,
        @JsonProperty("Created_At") String createdAt,
        @JsonProperty("Updated_At") String updatedAt
) {
    /**
     * Une méthode utilitaire pour conserver la compatibilité avec le reste de ton code
     * qui vérifie si la transaction est un succès.
     */
    public boolean isSuccess() {
        return "Success".equalsIgnoreCase(this.status);
    }
}