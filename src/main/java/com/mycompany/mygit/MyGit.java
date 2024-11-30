/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mygit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Marvin
 */
public class MyGit {

    // Initialize the repository
    public static void init() throws IOException {
        File mygitDir = new File(".mygit");
        if (mygitDir.exists()) {
            System.out.println("Repository already initialized.");
            return;
        }

        // Create directory structure
        new File(".mygit/objects").mkdirs();
        new File(".mygit/refs/heads").mkdirs();

        // Create HEAD file pointing to main branch
        File headFile = new File(".mygit/HEAD");
        try (FileWriter writer = new FileWriter(headFile)) {
            writer.write("refs/heads/main");
        }

        // Initialize main branch as empty (it will point to no commit initially)
        File mainBranchFile = new File(".mygit/refs/heads/main");
        mainBranchFile.createNewFile(); // Create the main branch reference file

        System.out.println("Initialized empty repository in .mygit");
    }

    // Clone the repository to a new directory
    public static void cloneRepository(String sourceDir, String targetDir) throws IOException {
        // Check if the target directory already exists
        File targetDirectory = new File(targetDir);
        if (targetDirectory.exists()) {
            System.out.println("Error: Target directory already exists.");
            return;
        }

        // Create target directory
        targetDirectory.mkdirs();

        // Define the .mygit folder inside the source and target directory
        File sourceGitDir = new File(sourceDir + "/.mygit");
        File targetGitDir = new File(targetDir + "/.mygit");

        // Check if source repository exists
        if (!sourceGitDir.exists()) {
            System.out.println("Error: Source repository does not have a .mygit directory.");
            return;
        }

        // Copy the .mygit directory from source to target
        copyDirectory(sourceGitDir, targetGitDir);

        System.out.println("Repository cloned successfully from " + sourceDir + " to " + targetDir);
    }

    // Helper method to copy a directory from source to destination
    private static void copyDirectory(File source, File target) throws IOException {
        if (source.isDirectory()) {
            // Create the target directory if it doesn't exist
            if (!target.exists()) {
                target.mkdir();
            }

            // Loop through the files in the source directory
            String[] files = source.list();
            if (files != null) {
                for (String file : files) {
                    File srcFile = new File(source, file);
                    File destFile = new File(target, file);
                    copyDirectory(srcFile, destFile);
                }
            }
        } else {
            // Copy the file content from source to target
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static void main(String[] args) {
        try {
            //Initialize the repository
            System.out.println("Initializing repository...");
            init();

            //Add a file to the staging area
            System.out.println("Adding a file to the staging area...");
            FileHandler.addToIndex("myFile.txt");

            //Commit the changes
            System.out.println("Committing the changes...");
            Commit.commit("Initial commit");

            //View commit history
            System.out.println("Viewing commit history...");
            Log.log();

            //Create a new branch
            System.out.println("Creating a new branch 'feature'...");
            Branch.createBranch("feature");

            //Switch to the new branch
            System.out.println("Switching to branch 'feature'...");
            Branch.switchBranch("feature");

            //Add a new file to the feature branch
            System.out.println("Adding a new file to the 'feature' branch...");
            FileHandler.addToIndex("newBranchFile.txt");
            Commit.commit("Added newBranchFile.txt");

            //View commit history in the feature branch
            System.out.println("Viewing commit history in the 'feature' branch...");
            Log.log();

            //Switch back to the main branch
            System.out.println("Switching back to branch 'main'...");
            Branch.switchBranch("main");

            //Merge 'main' into 'feature'
            System.out.println("Merging branch 'main' into 'feature'...");
            Branch.switchBranch("feature"); // Switch to 'feature' for merging
            Merge.mergeBranch("main");

            //View differences between branches
            System.out.println("Viewing differences between 'main' and 'feature'...");
            Diff.diffBranches("main", "feature");

            // Clone the repository to another directory
            System.out.println("Cloning the repository...");
            cloneRepository(".", "myClonedRepo");

            // Test ignored files
            FileHandler.addToIndex("myLogs.log"); // Should be ignored if in .mygitignore

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
