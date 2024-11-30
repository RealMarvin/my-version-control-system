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

/**
 *
 * @author Marvin
 */
public class Branch {

    // Create a new branch
    public static void createBranch(String branchName) throws IOException {
        // Check if the branch already exists
        File branchFile = new File(".mygit/refs/heads/" + branchName);
        if (branchFile.exists()) {
            System.out.println("Branch " + branchName + " already exists.");
            return;
        }

        // Copy the current commit hash (from the current branch) to the new branch
        File currentBranchFile = new File(".mygit/" + new String(Files.readAllBytes(Paths.get(".mygit/HEAD"))).trim());
        String currentCommitHash = new String(Files.readAllBytes(currentBranchFile.toPath()));

        // Create a new branch file with the same commit hash as the current branch
        try (FileWriter writer = new FileWriter(branchFile)) {
            writer.write(currentCommitHash);
        }

        // Update HEAD to point to the new branch
        try (FileWriter headWriter = new FileWriter(".mygit/HEAD")) {
            headWriter.write("refs/heads/" + branchName);
        }

        System.out.println("Created new branch: " + branchName);
    }

    // Switch to a branch
    public static void switchBranch(String branchName) throws IOException {
        File branchFile = new File(".mygit/refs/heads/" + branchName);
        if (!branchFile.exists()) {
            System.out.println("Branch does not exist: " + branchName);
            return;
        }

        // Update HEAD to point to the branch
        try (FileWriter writer = new FileWriter(".mygit/HEAD")) {
            writer.write("refs/heads/" + branchName);
        }

        System.out.println("Switched to branch: " + branchName);
    }
}
