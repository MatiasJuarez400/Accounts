package com.matiasjuarez.api.errorhandling.exceptions;

public class EntityNotFoundException extends Exception{
    private static final String ERROR_MESSAGE_MODEL = "Could not find entity for id: [%s]";

    public EntityNotFoundException(String entityId) {
        super(String.format(ERROR_MESSAGE_MODEL, entityId));
    }

    public EntityNotFoundException(long entityId) {
        super(String.format(ERROR_MESSAGE_MODEL, entityId));
    }
}
