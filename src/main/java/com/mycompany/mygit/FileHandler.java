/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mygit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Marvin
 */
public class FileHandler {

    // Method to add a file to the staging area
    public static void addToIndex(String fileName) throws IOException {
        // Check if file is ignored
        if (isFileIgnored(fileName)) {
            System.out.println("File " + fileName + " is ignored based on .mygitignore patterns.");
            return;
        }

        // Otherwise, add the file to the staging area
        Path indexPath = Paths.get(".mygit/index");
        File indexFile = indexPath.toFile();

        if (!indexFile.exists()) {
            indexFile.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile, true))) {
            writer.write(fileName + "\n");
            System.out.println("Added " + fileName + " to the staging area.");
        }
    }

    // Method to check if a file should be ignored based on .mygitignore
    private static boolean isFileIgnored(String fileName) throws IOException {
        File ignoreFile = new File(".mygitignore");
        if (!ignoreFile.exists()) {
            return false; // No .mygitignore file, no files are ignored
        }

        List<String> ignorePatterns = Files.readAllLines(ignoreFile.toPath());
        for (String pattern : ignorePatterns) {
            pattern = pattern.trim();

            if (pattern.isEmpty() || pattern.startsWith("#")) {
                continue; // Skip empty lines or comments
            }

            // Handle wildcard patterns, e.g., "*.log"
            if (Pattern.matches(convertGlobToRegex(pattern), fileName)) {
                return true;
            }
        }
        return false;
    }

    // Convert glob patterns (e.g., "*.log") to regex for matching
    private static String convertGlobToRegex(String glob) {
        glob = glob.replace(".", "\\.").replace("*", ".*");
        return "^" + glob + "$";
    }
}
