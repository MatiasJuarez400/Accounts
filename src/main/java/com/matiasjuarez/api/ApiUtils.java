package com.matiasjuarez.api;

import com.matiasjuarez.utils.JsonConverter;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Map;

public class ApiUtils {
    public static Integer convertRequestValueToInteger(String requestBodyKey, Map<String, Object> requestBody) {
        try {
            return Integer.valueOf(requestBody.get(requestBodyKey).toString());
        } catch (Exception e) {
            throw new BadRequestException(
                    String.format("Value for %s must be an integer. Received [%s]",
                            requestBodyKey, requestBody.get(requestBodyKey))
            );
        }
    }

    public static Long convertRequestValueToLong(String requestBodyKey, Map<String, Object> requestBody) {
        try {
            return Long.valueOf(requestBody.get(requestBodyKey).toString());
        } catch (Exception e) {
            throw new BadRequestException(
                    String.format("Value for %s must be an integer. Received [%s]",
                            requestBodyKey, requestBody.get(requestBodyKey))
            );
        }
    }

    public static BigDecimal convertRequestValueToBigDecimal(String requestBodyKey, Map<String, Object> requestBody) {
        try {
            return new BigDecimal((String) requestBody.get(requestBodyKey));
        } catch (Exception e) {
            throw new BadRequestException(
                    String.format("Value for %s must be a String with floating-point number format '##.##'. Received [%s]",
                            requestBodyKey, requestBody.get(requestBodyKey))
            );
        }
    }

    public static Boolean convertRequestValueToBoolean(String requestBodyKey, Map<String, Object> requestBody) {
        try {
            return Boolean.valueOf(requestBody.get(requestBodyKey).toString());
        } catch (Exception e) {
            throw new BadRequestException(
                    String.format("Value for %s must be a boolean. Received [%s]",
                            requestBodyKey, requestBody.get(requestBodyKey))
            );
        }
    }

    public static void validateIfValuesArePresent(Map<String, Object> requestBody, String... requiredValues) {
        if (requestBody == null) {
            throw new BadRequestException(buildRequiredValuesMessage(requiredValues));
        }

        for (String requiredValue : requiredValues) {
            if (!requestBody.containsKey(requiredValue)) {
                throw new BadRequestException(buildRequiredValuesMessage(requiredValues));
            }
        }
    }

    public static Response buildCreatedResponse(Object createdEntity) {
        return Response.status(Response.Status.CREATED).entity(JsonConverter.convert(createdEntity)).build();
    }

    public static Response buildOkResponse(Object responseBody) {
        return Response.ok(JsonConverter.convert(responseBody)).build();
    }

    private static String buildRequiredValuesMessage(String[] requiredValues) {
        StringBuilder sb = new StringBuilder("Value for ");
        sb.append(StringUtils.join(requiredValues, ", "))
                .append(" must be present in request body");

        return sb.toString();
    }
}
