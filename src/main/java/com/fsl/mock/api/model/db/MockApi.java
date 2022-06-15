package com.fsl.mock.api.model.db;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Introspected
@MappedEntity("mockapi")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MockApi {

    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String scenario;

    @NotNull
    private String name;

    @NotNull
    @NotBlank
    private String url;

    @NotNull
    @NotBlank
    private String method;

    private String queryParam;

    private String request_header;

    private String request_payload;

    @NotNull
    @NotBlank
    private String statusCode;

    private String responseHeader;

    private String responsePayload;

    private Integer delay;
}
