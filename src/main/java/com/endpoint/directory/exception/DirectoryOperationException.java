package com.endpoint.directory.exception;

public abstract class DirectoryOperationException  extends RuntimeException {
    public DirectoryOperationException(String message) {
        super(message);
    }
}
