package com.matiasjuarez.api.errorhandling.exceptions;

public class IllegalUpdateException extends RuntimeException {
    private static final String ERROR_MESSAGE_MODEL = "Parent %s with id [%s] does not have a child %s with id [%s]";

    public IllegalUpdateException(String parentEntityName, Object parentId, String childEntityName, Object childId) {
        super(String.format(ERROR_MESSAGE_MODEL, parentEntityName, parentId.toString(), childEntityName, childId.toString()));
    }
}
