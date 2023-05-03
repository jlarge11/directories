package com.endpoint.directory;

import java.util.List;

import static com.endpoint.directory.CommandVerb.CREATE;
import static com.endpoint.directory.CommandVerb.LIST;
import static com.endpoint.directory.CommandVerb.MOVE;
import static com.endpoint.directory.CommandVerb.DELETE;

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
        return List.of(
                "CREATE fruits",
                "CREATE vegetables",
                "CREATE grains",
                "CREATE fruits/apples",
                "CREATE fruits/apples/fuji",
                "LIST",
                "CREATE grains/squash",
                "MOVE grains/squash vegetables",
                "CREATE foods",
                "MOVE grains foods",
                "MOVE fruits foods",
                "MOVE vegetables foods",
                "LIST",
                "DELETE fruits/apples",
                "DELETE foods/fruits/apples",
                "LIST"
        );
    }
}
