package com.endpoint.directories;

import com.endpoint.directories.exception.DirectoryOperationException;
import com.endpoint.directories.model.CommandVerb;
import com.endpoint.directories.service.DirectoryService;
import com.endpoint.directories.service.InputReaderService;

import java.util.List;

import static com.endpoint.directories.model.CommandVerb.CREATE;
import static com.endpoint.directories.model.CommandVerb.LIST;
import static com.endpoint.directories.model.CommandVerb.MOVE;

public class Main {
    public static void main(String[] args) {
        DirectoryService directoryService = new DirectoryService();
        InputReaderService inputReaderService = new InputReaderService();
        List<String> commands = inputReaderService.getCommands();

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
}
