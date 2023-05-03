package com.endpoint.directories.exception;

public class UnableToDeleteException extends DirectoryOperationException {
    public UnableToDeleteException(String path, String pathElement) {
        super(String.format("Cannot delete %s - %s does not exist", path, pathElement));
    }
}
