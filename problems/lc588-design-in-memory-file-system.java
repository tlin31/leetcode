588. Design In-Memory File System - Hard

Design an in-memory file system to simulate the following functions:

	1. ls: Given a path in string format. If it is a file path, return a list that only 
	       contains this file name. 
	       If it is a directory path, return the list of file and directory names in this 
	       directory. 
	       Your output (file and directory names together) should in lexicographic order.

	2. mkdir: Given a directory path that does not exist, you should make a new directory 
	          according to the path. 
	          If the middle directories in the path do not exist either, you should create them as well. 
	          This function has void return type.

	3. addContentToFile: Given a file path and file content in string format. If the file does not 
	                     exist, you need to create that file containing given content. 
	                     If the file already exists, you need to append given content to original 
	                     content. This function has void return type.

	4. readContentFromFile: Given a file path, return its content in string format.

 

Example:

Input: 
["FileSystem","ls",  "mkdir",    "addContentToFile", "ls", "readContentFromFile"]
[[],           ["/"],["/a/b/c"],["/a/b/c/d","hello"],["/"], ["/a/b/c/d"]]

Output:
[null,[],null,null,["a"],"hello"]
 

Note:
1. You can assume all file or directory paths are absolute paths which begin with / and do not end 
with / except that the path is just "/".

2. You can assume that all operations will be passed valid parameters and users will not attempt to
retrieve file content or list a directory or file that does not exist.

3. You can assume that all directory names and file names only contain lower-case letters, and 
same names will not exist in the same directory.


******************************************************
key:
	- Trie
	- edge case:
		1) need create folder if it is not existed in addContentToFile
		2)

******************************************************

=======================================================================================================

method 0:
	- Used TreeMap to avoid sorting the list as insertion takes O(logN).
	- best solution
	- treat directory & file as the same
	- given in the problem note, "users will not attempt to retrieve file content or list a directory 
	  or file that does not exist", so always create sub-directory along the way in findNode function.

stats:

	- GetList method takes O(N)
	- 



public class FileSystem {
    private FileNode root;

    public FileSystem() {
        root = new FileNode("");
    }

    public List<String> ls(String path) {
        return findNode(path).getList();
    }

    public void mkdir(String path) {
        findNode(path);
    }

    public void addContentToFile(String filePath, String content) {
        findNode(filePath).addContent(content);
    }

    public String readContentFromFile(String filePath) {
        return findNode(filePath).getContent();
    }

    //-- private method section --//
    // doesn't matter if searching for file or a directory
    // stop when reach a file
    private FileNode findNode(String path){
        String[] files = path.split("/");

        FileNode cur = root;
        for(String file : files){
            if(file.length() == 0) 
            	continue;

            cur.children.putIfAbsent(file, new FileNode(file));
            cur = cur.children.get(file);

            // if reach a file, we can't have a directory under a file, so break
            if(cur.isFile()) 
            	break;
        }

        return cur;
    }

   // Private class
   private class FileNode{
        private TreeMap<String, FileNode> children;
        private StringBuilder fileContent;
        private String name;

        public FileNode(String name) {
            children = new TreeMap<>();
            fileContent = new StringBuilder();
            this.name = name;
        }

        public String getContent(){
            return fileContent.toString();
        }

        public String getName(){
            return name;
        }

        public void addContent(String content){
            fileContent.append(content);
        }

        // always check for fileContent, only work if a file is already written
        // valid here since we only have mkdir, no operation to create new file
        public boolean isFile(){
            return fileContent.length() > 0;
        }

        public List<String> getList(){
            List<String> list = new ArrayList<>();
            if(isFile()){
                list.add(getName());
            }else{
                list.addAll(children.keySet());
            }

            return list;
        }
    }
}

=======================================================================================================
method 1:

method:

	- Trie
	- Q: why dfs? bfs with 1 level should work fine

stats:

	- 
	- 

public class FileSystem {
    
    class Node {
    	// 1 - dir ; 2 - file
        int type = 0; 

        // use content to store file content.
        StringBuilder content;
        Node [] children = new Node[27];
    }
    
    // global
    Node root;

    public FileSystem() {
        root = new Node();
        root.type = 1;

        // for '/'
        root.children[26] = new Node();
    }
    
    
    
    public List<String> ls(String path) {
        List<String> list = new ArrayList<>();

        // if entire directory
        if ("/".equals(path)) {
            dfs(list, root.children[26], new StringBuilder());
            return list;
        }

        // find level of directory
        Node node = traverse(path, 1);

        // if it's a file, return the file name
        if (node.type == 2) {
            int k = path.length() - 1;
            while (k >= 0 && path.charAt(k) != '/') {
                k--;
            }
            list.add(path.substring(k + 1));
        } 
        else {

        	// is a directory, then list all sub-directory
            if (node.children[26] == null) {
                return list;
            }
            dfs(list, node.children[26], new StringBuilder());
        }
        return list;
    }
    
    public void mkdir(String path) {
        traverse(path, 1);
    }
    
    public void addContentToFile(String filePath, String content) {
        Node node = traverse(filePath, 2);
        if (node.content == null) {
            node.content = new StringBuilder();
        }
        node.content.append(content);
    }
    
    public String readContentFromFile(String filePath) {
        Node node = traverse(filePath, 2);
        if (node.content == null) {
            return "";
        }
        return node.content.toString();
    }

    //============= helper method
    Node traverse(String s, int type) {
        Node node = root;
        for (int i = 0; i < s.length(); i++) {
            int next = s.charAt(i) == '/' ? 26 : s.charAt(i) - 'a';

            // create new node if this path/subfolder doesn't exist
            if (node.children[next] == null) {
                node.children[next] = new Node();
            }

            node = node.children[next];

            // if next char is '/', then it's a directory,update node type as we traverse
            if (i + 1 < s.length() && s.charAt(i + 1) == '/') {
                node.type = 1;
            }
        }

        // if type is not initialized in last if
        if (node.type == 0) {
            node.type = type;
        }

        return node;
    }
   
    void dfs(List<String> list, Node root, StringBuilder sb) {
        if (root.type > 0) {
            list.add(sb.toString());
        }

        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                sb.append((char)('a' + i));
                dfs(list, root.children[i], sb);
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }
}


=======================================================================================================
method 2:

method:

	- unified filesfiles hashmap for directory, which contains the list of all the files and 
	  subdirectories in the current directory. 

	- we contain an entry isfile, which when True indicates that the current files 
	  entry is actually corresponding to a file, otherwise it represents a directory. 
	- Further, since we are considering the directory and files entries in the same manner, we 
	  need an entry for content, which contains the contents of the current file(if isfile entry is 
	  True in the current case). For entries corresponding to directories, the content field is kept empty.
	- similar to method 1, but uses hashmap

Tips:
	- use  String[] d = path.split("/");
	- addContentToFile also works well with append to previous write


stats:

	- time for "ls": O(m+n+klog(k)). 
	  Here, m refers to the length of the input string. We need to scan the input string once to split
	  it and determine the various levels. 
	  n refers to the depth of the last directory level in the given input for ls. This factor is taken
	  because we need to enter n levels of the tree structure to reach the last level. 
	  k refers to the number of entries(files+sub-directories) in the last level directory(in the current input). We need to sort these names giving a factor of klog(k).

	- time for "mkdir": O(m+n). 

	- time for both addContentToFile and readContentFromFile is O(m+n). 

Pro:
	- 1. expandable to include even more commands easily. For example, rmdir to remove a directory
	  given an input directory path. We need to simply reach to the destined directory level and 
	  remove the corresponding directory entry from the corresponding dirsdirs keys.

	- 2. Renaming files/directories is also very simple, since all we need to do is to create a 
	  temporary copy of the directory structure/file with a new name and delete the last entry.

	- 3. Relocating a hierarchichal subdirectory structure from one directory to the other is 
	  also very easy, since, all we need to do is obtain the address for the corresponding 
	  subdirectory c-lass, and assign the same at the new positon in the new directory structure.

Cons:
	- 1. If the number of directories is very large, we waste redundant space for isfile 
	     and content, which was not needed in the first design.

	- 2. if we want to list only the directories(and not the files), on any given path. In this 
	     case, we need to traverse over the whole contents of the current directory, check for 
	     each entry, whether it is a file or a directory, and then extract the required data.


	- 

public class FileSystem {
    class File {
        boolean isfile = false;
        HashMap < String, File > files = new HashMap < > ();
        String content = "";
    }
    
    File root;
    public FileSystem() {
        root = new File();
    }

    public List < String > ls(String path) {
        File t = root;
        List < String > files = new ArrayList < > ();

        // if not root file
        if (!path.equals("/")) {
            String[] d = path.split("/");

            // traverse to that level
            for (int i = 1; i < d.length; i++) {
                t = t.files.get(d[i]);
            }

            // if ls destination itself is a file
            if (t.isfile) {
                files.add(d[d.length - 1]);
                return files;
            }
        }

        List < String > res_files = new ArrayList < > (t.files.keySet());
        Collections.sort(res_files);
        return res_files;
    }

    public void mkdir(String path) {
        File t = root;
        String[] d = path.split("/");
        for (int i = 1; i < d.length; i++) {
        	// create file
            if (!t.files.containsKey(d[i]))
                t.files.put(d[i], new File());

            // move down the branch
            t = t.files.get(d[i]);
        }
    }

    public void addContentToFile(String filePath, String content) {
        File t = root;
        String[] d = filePath.split("/");
        for (int i = 1; i < d.length - 1; i++) {
            t = t.files.get(d[i]);
        }

        // if doesn't exist this file:
        if (!t.files.containsKey(d[d.length - 1]))
            t.files.put(d[d.length - 1], new File());

        t = t.files.get(d[d.length - 1]);
        t.isfile = true;
        t.content = t.content + content;
    }

    public String readContentFromFile(String filePath) {
        File t = root;
        String[] d = filePath.split("/");
        for (int i = 1; i < d.length - 1; i++) {
            t = t.files.get(d[i]);
        }
        return t.files.get(d[d.length - 1]).content;
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */

=======================================================================================================
method 3:

method:

	- https://leetcode.com/problems/design-in-memory-file-system/solution/
	- method 1, use 2 hashmaps, separate Directory and File List


