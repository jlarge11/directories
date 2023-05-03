package com.endpoint.directories.service;

import com.endpoint.directories.model.Directory;
import com.endpoint.directories.exception.UnableToDeleteException;
import com.endpoint.directories.exception.UnableToMoveException;

import java.util.*;

public class DirectoryService {
    private final Directory root = new Directory("");

    public void add(String path) {
        String[] pathElements = path.split("/");
        TreeMap<String, Directory> currentChildren = root.getChildren();

        for (String pathElement : pathElements) {
            if (currentChildren.containsKey(pathElement)) {
                currentChildren = currentChildren.get(pathElement).getChildren();
            } else {
                Directory newDirectory = new Directory(pathElement);
                currentChildren.put(pathElement, newDirectory);
                currentChildren = newDirectory.getChildren();
            }
        }
    }

    public void move(String sourcePath, String destinationPath) {
        String[] sourcePathElements = sourcePath.split("/");
        Directory sourceContainingDirectory = root;

        for (int i = 0; i < sourcePathElements.length - 1; i++) {
            String sourcePathElement = sourcePathElements[i];
            TreeMap<String, Directory> currentChildren = sourceContainingDirectory.getChildren();

            if (!currentChildren.containsKey(sourcePathElement)) {
                throw new UnableToMoveException(sourcePath, destinationPath, sourcePathElement, "source");
            }

            sourceContainingDirectory = currentChildren.get(sourcePathElement);
        }

        String[] destinationPathElements = destinationPath.split("/");
        Directory destinationDirectory = root;

        for (String destinationPathElement : destinationPathElements) {
            TreeMap<String, Directory> destinationChildren = destinationDirectory.getChildren();

            if (!destinationChildren.containsKey(destinationPathElement)) {
                throw new UnableToMoveException(sourcePath, destinationPath, destinationPathElement, "destination");
            }

            destinationDirectory = destinationChildren.get(destinationPathElement);
        }

        String lastSourcePathElement = sourcePathElements[sourcePathElements.length - 1];
        TreeMap<String, Directory> sourceContainingDirectoryChildren = sourceContainingDirectory.getChildren();
        Directory sourceDirectory = sourceContainingDirectoryChildren.get(lastSourcePathElement);
        sourceContainingDirectoryChildren.remove(lastSourcePathElement);
        destinationDirectory.getChildren().put(lastSourcePathElement, sourceDirectory);
    }

    public void delete(String path) {
        String[] pathElements = path.split("/");
        Directory containingDirectory = root;

        for (int i = 0; i < pathElements.length - 1; i++) {
            String pathElement = pathElements[i];
            TreeMap<String, Directory> currentChildren = containingDirectory.getChildren();

            if (!currentChildren.containsKey(pathElement)) {
                throw new UnableToDeleteException(path, pathElement);
            }

            containingDirectory = currentChildren.get(pathElement);
        }

        String lastPathElement = pathElements[pathElements.length - 1];

        containingDirectory.getChildren().remove(lastPathElement);
    }

    public String list() {
        return root.toString();
    }
}
