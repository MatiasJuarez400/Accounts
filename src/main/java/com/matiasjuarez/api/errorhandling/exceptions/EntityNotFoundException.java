package com.matiasjuarez.api.errorhandling.exceptions;

public class EntityNotFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE_MODEL = "Could not find entity for id: [%s]";
    private static final String ERROR_MESSAGE_MODEL_2 = "Could not find %s for id: [%s]";

    public EntityNotFoundException(String entityId) {
        super(String.format(ERROR_MESSAGE_MODEL, entityId));
    }

    public EntityNotFoundException(long entityId) {
        super(String.format(ERROR_MESSAGE_MODEL, entityId));
    }

    public EntityNotFoundException(String entityName, String entityId) {
        super(String.format(ERROR_MESSAGE_MODEL_2, entityName, entityId));
    }

    public EntityNotFoundException(String entityName, long entityId) {
        super(String.format(ERROR_MESSAGE_MODEL_2, entityName, entityId));
    }
}
