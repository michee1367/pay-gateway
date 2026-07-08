package com.villagesat.gateway.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger log = LoggerFactory.getLogger(RestClientLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        // Log de la requête sortante (Méthode, URI et Body JSON)
        log.info("=========================== REQUÊTE SORTANTE ===========================");
        log.info("URI          : {} {}", request.getMethod(), request.getURI());
        log.info("Headers      : {}", request.getHeaders());
        log.info("JSON Envoyé  : {}", new String(body, StandardCharsets.UTF_8));
        log.info("========================================================================");
        
        return execution.execute(request, body);
    }
}