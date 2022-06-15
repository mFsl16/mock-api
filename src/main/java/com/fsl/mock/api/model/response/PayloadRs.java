package com.fsl.mock.api.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micronaut.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PayloadRs {

    private String status;
    private Object data;

    @JsonIgnore
    private HttpStatus httpStatus;
}
