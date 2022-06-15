package com.fsl.mock.api.controller;

import java.util.Map;

import com.fsl.mock.api.usecase.MockHandlerUseCase;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@ExecuteOn(TaskExecutors.IO)
@Slf4j
@Controller
public class ApiController {

    @Inject
    private MockHandlerUseCase useCase;

    @ExecuteOn(TaskExecutors.IO)
    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public HttpResponse<?> requestHandler(HttpRequest<?> request) {
        String path = request.getPath();
        String method = request.getMethodName();
        log.info("[HANDLING MOCK PATH: {} METHOD: {}]", path, method);
        Map<String, String> respone = useCase.mockHandler(path, method);
        return HttpResponse.ok(respone);
    }
    
}
