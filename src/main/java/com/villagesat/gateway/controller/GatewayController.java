package com.villagesat.gateway.controller;

import com.villagesat.gateway.client.PayDrcClient;
import com.villagesat.gateway.dto.GatewayJobRequest;
import com.villagesat.gateway.dto.PayDrcRequest;
import com.villagesat.gateway.dto.PayDrcResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gateway")
public class GatewayController {

    private final PayDrcClient payDrcClient;

    public GatewayController(PayDrcClient payDrcClient) {
        this.payDrcClient = payDrcClient;
    }

    @PostMapping("/request")
    public ResponseEntity<PayDrcResponse> processGatewayRequest(@RequestBody GatewayJobRequest request) {
        
        // Construction du payload complet conforme à PayDRC
        PayDrcRequest payload = payDrcClient.buildPayload(
                request.phoneNumber(),
                request.amount().toPlainString(),
                request.currency(),
                request.action().toLowerCase(), // "debit" ou "credit"
                request.reference(),
                request.provider(), // "mpesa", "orange", etc.
                request.callbackUrl()
        );

        // Appel de la passerelle externe
        PayDrcResponse response = payDrcClient.sendToProvider(payload);

        return ResponseEntity.ok(response);
    }
}