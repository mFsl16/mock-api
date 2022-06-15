package com.fsl.mock.api.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

import com.fsl.mock.api.cache.ActiveMock;
import com.fsl.mock.api.model.db.MockApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class MockHandlerUseCase {
    
    private final ActiveMock activeMock;
    private final Gson gson;
    
    public MockHandlerUseCase(ActiveMock activeMock) {
        this.activeMock = activeMock;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public Map<String, String> mockHandler(String path, String method) {

        Map<String, String> response;
        try {

            MockApi mockApi = StreamSupport.stream(activeMock.getActiveMock().spliterator(), false)
                .filter(data -> data.getUrl().equalsIgnoreCase(path) && data.getMethod().equalsIgnoreCase(method))
                .findAny().orElse(null);

            if (mockApi != null) {
                response = gson.fromJson(mockApi.getResponsePayload(), Map.class);
                Thread.sleep(mockApi.getDelay());
            } else {
                Map<String, String> data = new HashMap<>();
                data.put("message", "mock not applied");
                response = data;
            }

            log.info("[{}][COMPLETE HANDLING MOCK]", this.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> data = new HashMap<>();
            data.put("message", e.getMessage());
            response = data;

        }

        return response;
    }
}
