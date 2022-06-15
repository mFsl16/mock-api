package com.fsl.mock.api.util;

import com.fsl.mock.api.exception.JsonFormatException;
import com.fsl.mock.api.model.db.MockApi;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommonUtil {

    public static Boolean isJsonValid(String json) throws JsonFormatException {
        Gson gson = new Gson();
        try {
            Map<String, Object> jsonStr = gson.fromJson(json, Map.class);
            new JSONObject(gson.toJson(jsonStr));
        } catch (Exception e) {
            throw new JsonFormatException(json + " : invalid json format");
        }
        return true;
    }

    public static void mockApiJsonValidation(MockApi mockApi) throws JsonFormatException {

        String requestHeader = mockApi.getRequest_header();
        String requestPayload = mockApi.getRequest_payload();
        String responseHeader = mockApi.getResponseHeader();
        String responsePayload = mockApi.getResponsePayload();

        if (requestHeader != null) {
            isJsonValid(requestHeader);
        }

        if (requestPayload != null) {
            isJsonValid(requestPayload);
        }

//        if (responseHeader != null) {
//            isJsonValid(responseHeader);
//        }

        if (responsePayload != null) {
            isJsonValid(responsePayload);
        }

    }
}
