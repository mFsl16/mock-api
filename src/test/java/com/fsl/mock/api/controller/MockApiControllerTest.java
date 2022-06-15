package com.fsl.mock.api.controller;

import com.fsl.mock.api.model.db.MockApi;
import com.fsl.mock.api.model.response.PayloadRs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@MicronautTest
class MockApiControllerTest {

    @Inject
    @Client("/")
    HttpClient httpClient;

    @Test
    void saveMockShouldSuccess() {

        MockApi mockApi = new MockApi()
                .setName("AmdocsDia")
                .setScenario("GetBalance")
                .setDelay(200)
                .setMethod("GET")
                .setResponsePayload("{code: 00, data: success, status: success}")
                .setStatusCode("200")
                .setUrl("/v1/balance");

        assertDoesNotThrow(() -> {
            HttpResponse<PayloadRs> exchangeResponse = httpClient.toBlocking().exchange(HttpRequest.POST("/api/v1/mockapi", mockApi), PayloadRs.class);
            Optional<String> body = exchangeResponse.getBody(String.class);
            log.info(body.orElse("no response body"));
        });
    }

    @Test
    void saveMockShouldReturnFailed() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        MockApi mockApi = new MockApi()
                .setName("AmdocsDia")
                .setScenario("GetBalance")
                .setDelay(200)
                .setMethod("GET")
                .setResponseHeader("{Content-Type: application/json}")
                .setResponsePayload("{code: 00, data: success, status: success}}")
                .setStatusCode("200")
                .setUrl("/v1/balance");

        log.info(gson.toJson(mockApi));

        assertThrows(HttpClientResponseException.class, () -> {
            HttpResponse<PayloadRs> rsHttpResponse = httpClient.toBlocking().exchange(HttpRequest.POST("/api/v1/mockapi", mockApi), PayloadRs.class);
        });
    }
}