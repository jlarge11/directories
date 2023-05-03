package com.endpoint.directory;

public class UnableToMoveException extends DirectoryOperationException {
    public UnableToMoveException(String sourcePath, String destinationPath, String pathElement, String sourceOrDestination) {
        super(String.format("Unable to move %s to %s - %s on %s does not exist",
                sourcePath, destinationPath, pathElement, sourceOrDestination));
    }
}
