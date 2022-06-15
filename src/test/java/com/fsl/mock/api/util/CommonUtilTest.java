package com.fsl.mock.api.util;

import com.google.gson.Gson;
import io.micronaut.http.MediaType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    @Test
    void isJsonValidTest() {
        String json = "{name: faisal, age: 12}";
        assertDoesNotThrow(() -> {
            System.out.println(CommonUtil.isJsonValid(json));
        });
    }

    @Test
    void testJson() {
        Gson gson = new Gson();
        String json = "{Content-Type: applicationjson}";
        Map<String, Object> map = (Map<String, Object>) gson.fromJson(json, HashMap.class);
        System.out.println(gson.toJson(MediaType.APPLICATION_JSON_TYPE));
    }
}