package com.endpoint.directories.exception;

public abstract class DirectoryOperationException  extends RuntimeException {
    public DirectoryOperationException(String message) {
        super(message);
    }
}
