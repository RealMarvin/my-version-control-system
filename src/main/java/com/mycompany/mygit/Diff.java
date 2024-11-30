/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mygit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Marvin
 */
public class Diff {

    // Compare two branches and show differences including conflicts
    public static void diffBranches(String branch1, String branch2) throws IOException {
        // Get commit hashes from the two branches
        String commitHash1 = getCommitHashForBranch(branch1);
        String commitHash2 = getCommitHashForBranch(branch2);

        // Compare the contents of the two commit objects
        Map<String, String> filesInCommit1 = getFilesFromCommit(commitHash1);
        Map<String, String> filesInCommit2 = getFilesFromCommit(commitHash2);

        // List of differences
        List<String> diffList = new ArrayList<>();
        List<String> conflictsList = new ArrayList<>();

        // Check files in commit 1 that are not in commit 2
        for (String fileName : filesInCommit1.keySet()) {
            if (!filesInCommit2.containsKey(fileName)) {
                diffList.add("File " + fileName + " exists in " + branch1 + " but not in " + branch2);
            } else if (!filesInCommit1.get(fileName).equals(filesInCommit2.get(fileName))) {
                // Detect conflict if file exists in both branches but differs
                conflictsList.add("Conflict detected in file " + fileName + " between branches " + branch1 + " and " + branch2);
            }
        }

        // Check files in commit 2 that are not in commit 1
        for (String fileName : filesInCommit2.keySet()) {
            if (!filesInCommit1.containsKey(fileName)) {
                diffList.add("File " + fileName + " exists in " + branch2 + " but not in " + branch1);
            }
        }

        // Output differences
        if (diffList.isEmpty()) {
            System.out.println("No differences between branches.");
        } else {
            System.out.println("Differences between branches " + branch1 + " and " + branch2 + ":");
            for (String diff : diffList) {
                System.out.println(diff);
            }
        }

        // Output conflicts
        if (!conflictsList.isEmpty()) {
            System.out.println("Conflicts between branches " + branch1 + " and " + branch2 + ":");
            for (String conflict : conflictsList) {
                System.out.println(conflict);
            }
        } else {
            System.out.println("No conflicts detected");
        }
    }

    // Helper method to get the commit hash for a branch
    private static String getCommitHashForBranch(String branchName) throws IOException {
        File branchFile = new File(".mygit/refs/heads/" + branchName);
        if (!branchFile.exists()) {
            throw new FileNotFoundException("Branch " + branchName + " does not exist.");
        }
        return new String(Files.readAllBytes(branchFile.toPath())).trim();
    }

    // Helper method to get files from a commit
    private static Map<String, String> getFilesFromCommit(String commitHash) throws IOException {
        Map<String, String> files = new HashMap<>();
        String commitDir = ".mygit/objects/" + commitHash.substring(0, 2);
        File commitFile = new File(commitDir + "/" + commitHash.substring(2));

        if (!commitFile.exists()) {
            throw new FileNotFoundException("Commit " + commitHash + " does not exist.");
        }

        // Read the commit file and extract file changes (simple version)
        List<String> commitLines = Files.readAllLines(commitFile.toPath());
        for (String line : commitLines) {
            if (line.startsWith("Staged files:")) {
                continue;
            }
            files.put(line.trim(), commitHash);  // Store file name and commit hash as simple example
        }

        return files;
    }
}
