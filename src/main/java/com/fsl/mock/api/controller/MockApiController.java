package com.fsl.mock.api.controller;

import javax.validation.Valid;

import com.fsl.mock.api.model.db.MockApi;
import com.fsl.mock.api.model.response.PayloadRs;
import com.fsl.mock.api.service.MockApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import lombok.extern.slf4j.Slf4j;

@ExecuteOn(TaskExecutors.IO)
@Slf4j
@Controller("/api/v1/mockapi")
public class MockApiController {
    
    private final MockApiService service;
    private final Gson gson;

    public MockApiController(MockApiService service) {
        this.service = service;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Post()
    public HttpResponse<?> saveMock(@Body @Valid MockApi mockApi) {
        
        log.info("[RECEIVE REQUEST][PAYLOAD: {}]", mockApi.toString());
        PayloadRs savedMockApi = service.saveNewMock(mockApi);
        log.info("[SAVE NEW MOCK COMPLETED]");
        
        return HttpResponse.status(savedMockApi.getHttpStatus()).body(savedMockApi);
    }

    @Get()
    public HttpResponse<?> getAllMock() {
        
        log.info("[RECEIVE REQUEST GET ALL MOCK]");
        Iterable<MockApi> mockLists = service.getAllMockApi();
        log.info("[REQUEST GET ALL MOCK COMPLETED]");

        return HttpResponse.ok(mockLists);
    }

    @Get("/scenario/{scenarioName}")
    public HttpResponse<?> getMockByScenario(@PathVariable String scenarioName) {

        log.info("[RECEIVE REQUEST][SCENARIO NAME: {}]", scenarioName);
        Iterable<MockApi> listMock = service.getMockByScenario(scenarioName);
        log.info("[REQUEST COMPLETED]");

        return HttpResponse.ok(listMock);
    }

    @Get("/apply/{scenarioName}")
    public HttpResponse<?> applyMock(@PathVariable String scenarioName) {

        String isSuccess = service.setActiveMock(scenarioName);

        return HttpResponse.ok(isSuccess);
    }
    
    @Get("/activemock")
    public HttpResponse<?> getActiveMock() {
        return HttpResponse.ok(service.getActiveMock());
    }

    @Put("/{id}")
    public HttpResponse<?> updateMock(@Body MockApi mockApi, @PathVariable Long id) {
        log.info("[RECEIVE REQUEST ID: {} PAYLOAD: {}]", id, mockApi.toString());
        PayloadRs mockApiRs = service.updateMock(id, mockApi);
        log.info("[UPDATE MOCK COMPLETED]");
        return HttpResponse.status(mockApiRs.getHttpStatus()).body(mockApiRs);
    }

}
