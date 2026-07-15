package com.villagesat.gateway.client;

import com.villagesat.gateway.dto.PayDrcRequest;
import com.villagesat.gateway.dto.PayDrcResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

@Component
public class PayDrcClient {

    private static final Logger log = LoggerFactory.getLogger(PayDrcClient.class);
    private final RestClient restClient;

    @Value("${paydrc.merchant-id}") private String merchantId;
    @Value("${paydrc.merchant-secret}") private String merchantSecret;
    //@Value("${paydrc.callback-url}") private String callbackUrl;

    public PayDrcClient(@Value("${paydrc.api-url}") String apiUrl) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(3).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(15).toMillis());

        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .requestFactory(factory)
                .requestInterceptor(new RestClientLoggingInterceptor())
                .build();
    }

    @Retry(name = "payDrcApi")
    public PayDrcResponse sendToProvider(PayDrcRequest request) {
        log.info("Envoi de la requête à PayDRC - Ref: {}, Méthode: {}", request.reference(), request.method());
        log.info("Parametre - Ref: {}, Méthode: {}", request.merchantId(), request.method());// Ajoute ces deux lignes juste au début de ta méthode sendToProvider(PayDrcRequest request) :
        log.info("Callback URL: {}", request.callbackUrl());// Ajoute ces deux lignes juste au début de ta méthode sendToProvider(PayDrcRequest request) :
        
        try {
            String jsonBrut = new ObjectMapper().writeValueAsString(request);
            System.out.println("====== JSON BRUT SANS TRADUCTION ======\n" + jsonBrut + "\n=======================================");
            
            return new PayDrcResponse(
                    "SUCCESS", 
                    0.0, 
                    "Test Ok", 
                    null, null, request.reference(), null, null, null

            );
            
        } catch (Exception e) {
            // Sécurité en cas de mauvaise réponse inattendue non capturée par onStatus
            //log.error("Erreur HTTP inattendue : Code {}, Corps : {}", e.getStatusCode(), e.getResponseBodyAsString());
            return new PayDrcResponse(
                    "Failed", 
                    0.0, 
                    "Erreur HTTP : " + e.getMessage(), 
                    null, null, null, null, null, null
            );
        }
        /* 

        try {
            return restClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    // 1. Gestion des erreurs clients (400, 401, 403, 404, etc.)
                    // On ne veut généralement PAS de Retry automatique de Resilience4j sur un 400 ou 403 (identifiants invalides ou données fausses).
                    .onStatus(HttpStatusCode::is4xxClientError, (req, response) -> {
                        String rawBody = new String(response.getBody().readAllBytes());
                        log.error("Erreur Client API PayDRC [{}] : {}", response.getStatusCode(), rawBody);
                        throw new IllegalArgumentException("Échec de la requête passerelle (Erreur 4xx) : " + rawBody);
                    })
                    // 2. Gestion des erreurs serveurs (500, 502, 503, 504)
                    // Cette exception va être interceptée par @Retry pour tenter de rejouer la requête si le réseau ou l'opérateur a sauté.
                    .onStatus(HttpStatusCode::is5xxServerError, (req, response) -> {
                        String rawBody = new String(response.getBody().readAllBytes());
                        log.warn("Erreur Serveur API PayDRC [{}] : {}", response.getStatusCode(), rawBody);
                        throw new IllegalStateException("Le serveur PayDRC rencontre un problème (Erreur 5xx)");
                    })
                    .body(PayDrcResponse.class);

        } catch (RestClientResponseException ex) {
            // Sécurité en cas de mauvaise réponse inattendue non capturée par onStatus
            log.error("Erreur HTTP inattendue : Code {}, Corps : {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            return new PayDrcResponse(
                    "Failed", 
                    0.0, 
                    "Erreur HTTP : " + ex.getStatusText(), 
                    null, null, null, null, null, null
            );
            
        } catch (IllegalArgumentException ex) {
            // On attrape l'erreur 4xx (Erreurs d'identifiants, données manquantes, etc.)
            return new PayDrcResponse(
                    "Failed", 
                    0.0, 
                    ex.getMessage(), 
                    null, null, null, null, null, null
            );
            
        } catch (Exception ex) {
            // Gère les Timeouts ou coupures de connexion réseau brutales
            log.error("Impossible de joindre le serveur PayDRC (Timeout ou Réseau) : {}", ex.getMessage());
            // Lever une RuntimeException ici permet à @Retry (Resilience4j) de relancer la transaction
            throw new RuntimeException("Passerelle PayDRC temporairement injoignable, nouvelle tentative...", ex);
        }*/

    }

    public PayDrcRequest buildPayload(String phone, String amount, String currency, String action, String ref, String method, String callbackUrl) {
        return new PayDrcRequest(
                merchantId, merchantSecret, amount, currency, action, phone,
                "agromwinda", "agromwinda", "agromwinda@gmail.com",
                ref, method.toLowerCase(), callbackUrl
        );
    }
}