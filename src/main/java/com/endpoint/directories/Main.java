package com.endpoint.directories;

import com.endpoint.directories.exception.FileReadException;

import com.endpoint.directories.exception.DirectoryOperationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.endpoint.directories.CommandVerb.CREATE;
import static com.endpoint.directories.CommandVerb.LIST;
import static com.endpoint.directories.CommandVerb.MOVE;

public class Main {
    public static void main(String[] args) {
        DirectoryService directoryService = new DirectoryService();
        InputReaderService inputReaderService = new InputReaderService();
        List<String> commands = getCommands();

        for (String command : commands) {
            System.out.println(command);
            String[] parsedCommand = command.split(" ");
            CommandVerb commandVerb = CommandVerb.valueOf(parsedCommand[0]);

            try {
                executeCommand(directoryService, parsedCommand, commandVerb);
            } catch (DirectoryOperationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void executeCommand(DirectoryService directoryService, String[] parsedCommand, CommandVerb commandVerb) {
        if (CREATE == commandVerb) {
            String path = parsedCommand[1];
            directoryService.add(path);
        } else if (LIST == commandVerb) {
            System.out.println(directoryService.list());
        } else if (MOVE == commandVerb) {
            String sourcePath = parsedCommand[1];
            String destinationPath = parsedCommand[2];
            directoryService.move(sourcePath, destinationPath);
        } else { // DELETE
            String path = parsedCommand[1];
            directoryService.delete(path);
        }
    }

    private static List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        FileInputStream fis = null;

        try (InputStream in = Main.class.getResourceAsStream("/input.txt")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            reader.lines().forEach(line -> {
                commands.add(line);
            });

            return commands;

        } catch (Exception e) {
            throw new FileReadException("Unable to read the input command file.", e);
        }
    }
}
