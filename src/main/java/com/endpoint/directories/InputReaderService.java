package com.endpoint.directories;

import com.endpoint.directories.exception.FileReadException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputReaderService {
    public List<String> getCommands() {
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
