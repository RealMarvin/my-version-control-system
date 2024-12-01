## Documentation
### Overview
This program is a simplified distributed source control system inspired by Git. It allows users to initialize a repository, stage files, commit changes, create branches, merge branches, view commit history, detect conflicts and clone repositories. Additionally, it supports ignoring files using a .mygitignore file. The entire repository is stored in a .mygit directory, mimicking the behavior of Git’s .git directory.

### Features
#### 1.	Repository Initialization
&emsp;o	Creates a .mygit directory to store metadata like commits, branches and references.
#### 2.	Staging Files
&emsp;o	Allows users to add files to an "index" before committing.
#### 3.	Committing Changes
&emsp;o	Captures the current state of staged files as a commit and stores them with unique IDs.
#### 4.	View Commit History
&emsp;o	Displays all previous commits with their IDs and messages.
#### 5.	Branching
&emsp;o	Supports creating new branches and switching between them.
#### 6.	Merging
&emsp;o	Combines changes from one branch into another, detecting conflicts if they occur.
#### 7.	File Comparison
&emsp;o	Compares files, detects conflict and retrieves commit data.
#### 8.	Cloning
&emsp;o	Enables cloning a repository to another directory on disk.
#### 9.	Ignoring Files
&emsp;o	Allows the user to specify patterns of files or directories to exclude using a .mygitignore file.

### How it works
#### 1. File Structure
All repository metadata is stored in the .mygit directory, organized as follows:<br/>
&ensp;•	**refs/heads/:** Contains branch pointers, with each file representing a branch.<br/>
&ensp;•	**objects/:** Stores commit objects and the states of files.<br/>
&ensp;•	**index:** Tracks staged files.<br/>
&ensp;•	**HEAD:** Points to the current branch.<br/>

#### 2. Core Classes and Methods
#### MyGit Class
Handles repository initialization and cloning.<br/><br/>
**(a) init():** <br/>
&emsp;o	Creates the .mygit directory and its subdirectories.<br/><br/>
**(b) cloneRepository(String source, String destination):** <br/>
&emsp;o	Copies the entire .mygit directory to a new location.<br/>
#### FileHandler Class
Manages file staging and .mygitignore.<br/><br/>
**(a) addToIndex(String fileName):** <br/>
&emsp;o	Adds a file to the staging area, skipping ignored files.<br/><br/>
**(b) isFileIgnored(String fileName):** <br/>
&emsp;o	Checks if a file matches patterns in .mygitignore. <br/>

#### Commit Class
Handles creating commits. <br/><br/>
**(a) commit(String message):** <br/>
&emsp;o	Creates a new commit, linking it to the previous one. <br/>
&emsp;o	Generates a unique ID for each commit. <br/>

#### Log Class
Reads the commit history from the .mygit directory.<br/><br/>
**(a) log():** <br/>
&emsp;o	Retrieves and prints the content of commits by following the parent chain. <br/>

#### Branch Class
Manages branching.<br/><br/>
**(a) createBranch(String branchName):** <br/>
&emsp;o	Creates a new branch pointing to the current commit.<br/><br/>
**(b) switchBranch(String branchName):** <br/>
&emsp;o	Switches to a new branch to add a new file.<br/>

#### Merge Class
Provides functionality to merge a specified branch into the current branch in the .mygit system.<br/><br/>
**(a) mergeBranch(String branchName):** <br/>
&emsp;o	Merges a branch into the current branch. <br/><br/>
**(b) findCommonAncestor(String commit1, String commit2):** <br/>
&emsp;o	Placeholder logic to find a common ancestor commit between two commits. <br/>
&emsp;o	Currently assumes commit1 is an ancestor of commit2. <br/><br/>
**(c) performThreeWayMerge(String ancestor, String current, String target):** <br/>
&emsp;o	Placeholder for implementing three-way merge logic. <br/>
&emsp;o	Returns an empty HashSet to indicate no conflicts. <br/>

#### Diff Class
Handles file comparison, conflict detection and retrieval of commit data.<br/><br/>
**(a) diffBranches(String branch1, String branch2)** <br/>
&emsp;o	Compares two branches and prints the differences and conflicts between their file sets.<br/><br/>
**(b) getCommitHashForBranch(String branchName)** <br/>
&emsp;o	Retrieves the latest commit hash for a specified branch.<br/><br/>
**(c) getFilesFromCommit(String commitHash)** <br/>
&emsp;o	Extracts the file’s details associated with a specific commit.<br/>

### .mygitignore Implementation
**(a) Pattern Matching:** <br/>
&emsp;o	Patterns like *.log or specific filenames are supported.<br/>
&emsp;o	Uses regular expressions to translate patterns into file-matching logic.<br/><br/>
**(b) Integration:** <br/>
&emsp;o	Before adding a file to the index, the program checks if it matches any ignore pattern.<br/>

### Interesting Aspects
#### 1. .mygitignore File Integration
The .mygitignore implementation mirrors Git’s behavior, allowing users to define patterns for files or directories to ignore. The code dynamically matches filenames against patterns using regular expressions, making it powerful yet simple.
#### 2. Branching and Conflict Detection
The program supports creating and merging branches while detecting merge conflicts. Although simplistic, this demonstrates the core challenges of source control systems.

#### 3. File Comparison
The program handles file comparison, conflict detection and retrieval of commit data.
#### 4. Repository Cloning
Cloning a repository on disk is a direct yet effective way to simulate distributed source control. By copying the .mygit directory, the program ensures all metadata and history are retained.
#### 5. Modular Design
Each functionality (commit, branching, merging, files’ comparison, logging and file handling) is encapsulated in separate classes, making the codebase extensible.

### Conclusion
This program demonstrates the fundamentals of a distributed source control system. From handling commits and branches to comparing files, ignoring files and cloning repositories. It showcases core concepts of source control systems such as Git. The .mygitignore implementation and the modular architecture make it particularly interesting and extensible for future enhancements.

