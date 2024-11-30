/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mygit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

/**
 *
 * @author Marvin
 */
public class Commit {

    public static void commit(String message) throws IOException {
        // Check if there are staged files
        File indexFile = new File(".mygit/index");
        if (!indexFile.exists() || indexFile.length() == 0) {
            System.out.println("No changes to commit.");
            return;
        }

        // Create a commit object (for simplicity, just write the message)
        String commitHash = UUID.randomUUID().toString();

        // Create directory structure for storing the commit object
        String commitDir = ".mygit/objects/" + commitHash.substring(0, 2); // First two chars as directory
        File commitDirFile = new File(commitDir);
        if (!commitDirFile.exists()) {
            commitDirFile.mkdirs(); // Create directory if it doesn't exist
        }

        // Create the commit file and write commit data
        File commitFile = new File(commitDir + "/" + commitHash.substring(2));
        try (FileWriter writer = new FileWriter(commitFile)) {
            writer.write("Commit message: " + message + "\n");
            writer.write("Staged files:\n");
            writer.write(Files.readString(indexFile.toPath()));
        }

        // Update the current branch (default: main)
        String headContent = new String(Files.readAllBytes(Paths.get(".mygit/HEAD")));
        File branchFile = new File(".mygit/" + headContent.trim());
        try (FileWriter writer = new FileWriter(branchFile)) {
            writer.write(commitHash);
        }

        // Clear the staging area
        new FileWriter(indexFile, false).close();

        System.out.println("Committed changes with hash: " + commitHash);
    }

}
