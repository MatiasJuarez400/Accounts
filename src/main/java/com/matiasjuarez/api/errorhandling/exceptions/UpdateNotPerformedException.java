package com.matiasjuarez.api.errorhandling.exceptions;

public class UpdateNotPerformedException extends RuntimeException {
    private static final String ERROR_MESSAGE_MODEL = "Unable to perform update on entity with id: [%s]";
    private static final String ERROR_MESSAGE_MODEL_2 = "Unable to perform update on %s with id: [%s]";

    public UpdateNotPerformedException(String entityId) {
        super(String.format(ERROR_MESSAGE_MODEL, entityId));
    }

    public UpdateNotPerformedException(long entityId) {
        super(String.format(ERROR_MESSAGE_MODEL, entityId));
    }

    public UpdateNotPerformedException(String entityName, String entityId) {
        super(String.format(ERROR_MESSAGE_MODEL_2, entityName, entityId));
    }

    public UpdateNotPerformedException(String entityName, long entityId) {
        super(String.format(ERROR_MESSAGE_MODEL_2, entityName, entityId));
    }
}
