package com.endpoint.directories;

import com.endpoint.directories.exception.FileReadException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputReaderService {
    public List<String> getCommands() {
        List<String> commands = new ArrayList<>();
        FileInputStream fis = null;

        try (InputStream in = this.getClass().getResourceAsStream("/input.txt")) {
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
