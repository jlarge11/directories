package com.endpoint.directories;

import java.util.TreeMap;

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

    public void add(String folderName) {
        children.put(folderName, new Directory(folderName));
    }

    public void delete(String folderName) {
        children.remove(folderName);
    }

    @Override
    public String toString() {
        return printChildren(this, 0);
    }

    private String printChildren(Directory directory, int indent) {
        TreeMap<String, Directory> children = directory.getChildren();

        if (children.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Directory child : children.values()) {
            sb
                    .append("  ".repeat(indent))
                    .append(child.name)
                    .append("\n")
                    .append(printChildren(child, indent + 1));
        }

        // Remove the trailing newline
        if (indent == 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
