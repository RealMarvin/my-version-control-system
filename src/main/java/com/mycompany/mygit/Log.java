/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mygit;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Marvin
 */
public class Log {
    public static void log() throws Exception {
        String headContent = new String(Files.readAllBytes(Paths.get(".mygit/HEAD")));
        String branch = headContent.split("/")[2].trim();
        File branchFile = new File(".mygit/refs/heads/" + branch);
        
        if (!branchFile.exists()) {
            System.out.println("No commits yet.");
            return;
        }
        
        String commitOid = new String(Files.readAllBytes(branchFile.toPath())).trim();
        while (commitOid != null && !commitOid.isEmpty()) {
            String commitFile = ".mygit/objects/" + commitOid.substring(0, 2) + "/" + commitOid.substring(2);
            String commitContent = new String(Files.readAllBytes(Paths.get(commitFile)));
            System.out.println(commitContent);
            
            if (commitContent.contains("parent")) {
                commitOid = commitContent.split("\n")[1].split(" ")[1];
            } else {
                commitOid = null;
            }
        }
    }
}
