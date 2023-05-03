package com.endpoint.directory;

import java.util.*;

public class Directory {
    private String name;
    private TreeMap<String, Directory> children;

    public Directory(String name) {
        this.name = name;
        this.children = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public TreeMap<String, Directory> getChildren() {
        return children;
    }

    public void add(String path) {
        String[] pathElements = path.split("/");
        TreeMap<String, Directory> currentChildren = children;

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
        Directory sourceContainingDirectory = this;

        for (int i = 0; i < sourcePathElements.length - 1; i++) {
            String sourcePathElement = sourcePathElements[i];
            TreeMap<String, Directory> currentChildren = sourceContainingDirectory.getChildren();

            if (!currentChildren.containsKey(sourcePathElement)) {
                throw new UnableToMoveException(sourcePath, destinationPath, sourcePathElement, "source");
            }

            sourceContainingDirectory = currentChildren.get(sourcePathElement);
        }

        String[] destinationPathElements = destinationPath.split("/");
        Directory destinationDirectory = this;

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
        Directory containingDirectory = this;

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

    @Override
    public String toString() {
        return print(this, 0);
    }

    private String print(Directory directory, int indent) {
        StringBuilder sb = new StringBuilder();

        for (Directory child : directory.getChildren().values()) {
            sb
                .append("  ".repeat(indent))
                .append(child.name)
                .append("\n")
                .append(print(child, indent + 1));
        }

        // Remove the trailing newline
        if (indent == 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
