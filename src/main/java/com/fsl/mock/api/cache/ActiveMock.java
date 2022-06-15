package com.fsl.mock.api.cache;

import java.util.ArrayList;

import com.fsl.mock.api.model.db.MockApi;

import io.micronaut.core.annotation.ReflectiveAccess;
import jakarta.inject.Singleton;

@ReflectiveAccess
@Singleton
public class ActiveMock {
    
    Iterable<MockApi> activeMocks = new ArrayList<>();

    public Iterable<MockApi> getActiveMock () {
        return this.activeMocks;
    }

    public void setActiveMock(Iterable<MockApi> newActiveMock) {
        this.activeMocks = newActiveMock;
    }


}
