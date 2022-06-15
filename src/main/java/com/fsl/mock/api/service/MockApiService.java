package com.fsl.mock.api.service;

import java.util.Optional;

import com.fsl.mock.api.cache.ActiveMock;
import com.fsl.mock.api.exception.JsonFormatException;
import com.fsl.mock.api.model.db.MockApi;
import com.fsl.mock.api.model.response.PayloadRs;
import com.fsl.mock.api.repository.MockApiRepository;
import com.fsl.mock.api.util.CommonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class MockApiService {

    private final ActiveMock activeMock;
    private final MockApiRepository repository;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public MockApiService(MockApiRepository repository, ActiveMock activeMock) {
        this.repository = repository;
        this.activeMock = activeMock;
    }

    public PayloadRs saveNewMock(MockApi mockApi) {

        PayloadRs payloadRs;

        try {
            CommonUtil.mockApiJsonValidation(mockApi);
            MockApi save = repository.save(mockApi);
            payloadRs = new PayloadRs()
                    .setHttpStatus(HttpStatus.OK)
                    .setStatus("success")
                    .setData(save);
        } catch(JsonFormatException exception) {
            payloadRs = new PayloadRs()
                    .setHttpStatus(HttpStatus.BAD_REQUEST)
                    .setStatus("failed")
                    .setData(exception.getMessage());
        }
        return payloadRs;
    }

    public Iterable<MockApi> getAllMockApi() {
        return repository.findAll();
    }

    public Iterable<MockApi> getMockByScenario(String scenarioName) {
        return repository.findByScenario(scenarioName);
    }

    public String setActiveMock(String scenarioName) {
        Iterable<MockApi> listMock = repository.findByScenario(scenarioName);
        log.info(gson.toJson(listMock));
        if(!listMock.iterator().hasNext()) {
            return "failed to apply mock";
        }
        activeMock.setActiveMock(listMock);

        return "success apply mock";
    }

    public Iterable<MockApi> getActiveMock() {
        return activeMock.getActiveMock();
    }

    public PayloadRs updateMock(Long id, MockApi mockApi) {

        PayloadRs payloadRs;

        try {
            Optional<MockApi> mOptional = repository.findById(id);
            if(mOptional.isPresent()) {
                CommonUtil.mockApiJsonValidation(mockApi);
                mockApi.setId(id);
                MockApi update = repository.update(mockApi);
                payloadRs = new PayloadRs()
                        .setHttpStatus(HttpStatus.OK)
                        .setStatus("success")
                        .setData(update);
            } else {
                payloadRs = new PayloadRs()
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .setStatus("failed")
                        .setData("Mock with id : " + id + " not found");
            }

        } catch (JsonFormatException e) {
            payloadRs = new PayloadRs()
                    .setHttpStatus(HttpStatus.BAD_REQUEST)
                    .setStatus("failed")
                    .setData(e.getMessage());
        }

        return payloadRs;

    }

    
}
