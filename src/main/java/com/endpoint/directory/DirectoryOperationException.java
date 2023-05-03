package com.endpoint.directory;

public abstract class DirectoryOperationException  extends RuntimeException {
    public DirectoryOperationException(String message) {
        super(message);
    }
}
