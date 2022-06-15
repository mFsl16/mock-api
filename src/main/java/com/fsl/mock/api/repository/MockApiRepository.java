package com.fsl.mock.api.repository;


import com.fsl.mock.api.model.db.MockApi;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository
public interface MockApiRepository extends CrudRepository<MockApi, Long> {
    
    Iterable<MockApi> findByScenario(String scenarioName);
}
