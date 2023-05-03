package com.endpoint.directories;

import com.endpoint.directories.exception.DirectoryOperationException;
import com.endpoint.directories.exception.FileReadException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.endpoint.directories.CommandVerb.CREATE;
import static com.endpoint.directories.CommandVerb.LIST;
import static com.endpoint.directories.CommandVerb.MOVE;

public class Main {
    public static void main(String[] args) {
        List<String> commands = getCommands();
        Directory root = new Directory("");

        for (String command : commands) {
            System.out.println(command);
            String[] parsedCommand = command.split(" ");
            CommandVerb commandVerb = CommandVerb.valueOf(parsedCommand[0]);

            try {
                executeCommand(root, parsedCommand, commandVerb);
            } catch (DirectoryOperationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void executeCommand(Directory root, String[] parsedCommand, CommandVerb commandVerb) {
        if (CREATE == commandVerb) {
            String path = parsedCommand[1];
            root.add(path);
        } else if (LIST == commandVerb) {
            System.out.println(root);
        } else if (MOVE == commandVerb) {
            String sourcePath = parsedCommand[1];
            String destinationPath = parsedCommand[2];
            root.move(sourcePath, destinationPath);
        } else { // DELETE
            String path = parsedCommand[1];
            root.delete(path);
        }
    }

    private static List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        FileInputStream fis = null;

        try {
            ClassLoader classLoader = Main.class.getClassLoader();
            File inputFile = new File(classLoader.getResource("input.txt").getFile());
            fis = new FileInputStream(inputFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String command;

            while ((command = reader.readLine()) != null) {
                commands.add(command);
            }

            return commands;
        } catch (Exception e) {
            throw new FileReadException("Unable to read input file.", e);
        }
    }
}
