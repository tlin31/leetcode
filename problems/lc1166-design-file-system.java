1166. Design File System - Medium

You are asked to design a file system which provides two functions:

1. createPath(path, value): Creates a new path and associates a value to it if possible and 
returns True. Returns False if the path already exists or its parent path doesn't exist.

2. get(path): Returns the value associated with a path or returns -1 if the path doesn't exist.

The format of a path is one or more concatenated strings of the form: / followed by one or more lowercase English letters. For example, /leetcode and /leetcode/problems are valid paths while an empty string and / are not.

Implement the two functions.

Please refer to the examples for clarifications.

 

Example 1:

Input: 
["FileSystem","createPath","get"]
[[],["/a",1],["/a"]]
Output: 
[null,true,1]

Explanation: 
FileSystem fileSystem = new FileSystem();
fileSystem.createPath("/a", 1); // return true
fileSystem.get("/a"); // return 1

Example 2:
Input: 
["FileSystem","createPath","createPath","get","createPath","get"]
[[],["/leet",1],["/leet/code",2],["/leet/code"],["/c/d",1],["/c"]]
Output: 
[null,true,true,2,false,-1]

Explanation: 
FileSystem fileSystem = new FileSystem();
fileSystem.createPath("/leet", 1); // return true
fileSystem.createPath("/leet/code", 2); // return true
fileSystem.get("/leet/code"); // return 2
fileSystem.createPath("/c/d", 1); // return false because the parent path "/c" doesn't exist.
fileSystem.get("/c"); // return -1 because this path doesn't exist.
 

Constraints:

The number of calls to the two functions is less than or equal to 10^4 in total.
2 <= path.length <= 100
1 <= value <= 10^9
NOTE: create method has been changed on August 29, 2019 to createPath. Please reset to default 
code definition to get new method signature.

******************************************************
key:
    - Hashmap to store directory & value
    - edge case:
        1) empty string, return empty
        2)

******************************************************

=======================================================================================================

Method:

    - airbnb full class Solution
    - input entire path

watchMap 是一个用于路径监听的回调注册表。每个路径可以注册一个监听器，当该路径或其子路径发生创建事件时，
监听器会被触发执行。
它实现类似文件系统或 Zookeeper 的 层级 Watch 机制：不仅监听当前节点，也监听所有祖先节点。

给某条路径注册一个回调（Runnable），当该路径下发生 create 操作时触发。

也就是：当文件系统中某个路径发生变化时自动触发对应的监听器。

类似 Zookeeper watch 或 文件系统事件监听。

class Solution {
    public static void main(String[] args) {
        AFileSystem fileSystem = new AFileSystem();
        System.out.println(fileSystem.create("a", 1));
        System.out.println(fileSystem.create("/a", 1));
        System.out.println(fileSystem.create("/a/b", 2));
        System.out.println(fileSystem.create("/c/b", 3));
        fileSystem.watch("/a/b", "on path /a/b");
        fileSystem.watch("/a", "on path /a");
        fileSystem.watch("/", "on path /");
        System.out.println(fileSystem.create("/a/b/c", 2));
        System.out.println(fileSystem.get("/a/b/c"));
        System.out.println(fileSystem.get("/a/b/c/d"));
    }
}

public class AFileSystem {
    Map<String, Integer> map;
    Map<String, Runnable> watchMap;

    public AFileSystem() {
        this.map = new HashMap<>();
        this.watchMap = new HashMap<>();
        map.put("/", 0);
    }

    public boolean create(String path, int val)  {
        if (map.containsKey(path)) {
            return false;
        }
        int index = path.lastIndexOf("/");

        // if doesn't have 分隔符 "/"
        // if don't contain previous path
        if (index == -1 || !map.containsKey(path.substring(0, index)) && index != 0) {
            return false;
        }

        map.put(path, val);

        //1. 创建新节点   2.向上不断截断路径     3. 只要某个路径被注册了 watcher，就执行它
        while (path.length() > 0) {
            if (watchMap.containsKey(path)) {
                watchMap.get(path).run();
            }
            path = path.substring(0, path.lastIndexOf("/"));
        }

        return true;
    }

    public Integer get(String path) {
        return map.get(path);
    }

    public boolean watch(String path, String message) {
        if (!map.containsKey(path)) {
            return false;
        }
        watchMap.put(path, () -> {
            System.out.println(message);
        });
        return true;
    }
}

=======================================================================================================
method 1:

method:

    - Trie tree
    - 

stats:

    - 
    - 

class FileSystem {
    class File{
        String name;
        int val = -1;
        Map<String, File> map = new HashMap<>();
        
        File(String name){
            this.name = name;
        }
    }

    File root;

    public FileSystem() {
        root = new File("");
    }
    
    public boolean create(String path, int value) {
        String[] array = path.split("/");
        File cur = root;
        
        for(int i=1;i<array.length;i++){
            String cur_name = array[i];
            if(cur.map.containsKey(cur_name)==false){
                // last path
                if(i == array.length-1){
                    cur.map.put(cur_name, new File(cur_name));
                }else{
                    return false;
                }
            }
            cur = cur.map.get(cur_name);
        }
        
        if(cur.val!=-1){
            return false;
        }
        
        cur.val = value;
        return true;
    }
    
    public int get(String path) {
        String[] array = path.split("/");
        File cur = root;
        for(int i=1;i < array.length;i++){
            String cur_name = array[i];
            if(cur.map.containsKey(cur_name)==false){
                return -1;
            }
            cur = cur.map.get(cur_name);
        }
        
        return cur.val;
        
        
    }
}


=======================================================================================================
method 2:

method:

    - 
    - 

stats:

    - Time: create(): O(path.length()), get(): O(1).

    - Space: file():
        In worst case, e.g., path = "/a/b/c/d/e/f/g/...", all the path family cost 
        2 + 4 + 6 + ... + 2n = n * (n + 1). So the space cost O(path.length() ^ 2).


class FileSystem {

    Map<String, Integer> file = new HashMap<>(); 
    
    public FileSystem() {
        file.put("", -1);
    }
    
    // put entire path
    public boolean create(String path, int value) {
        int idx = path.lastIndexOf("/");
        String parent = path.substring(0, idx);
        if (!file.containsKey(parent)) { return false; }
        return file.putIfAbsent(path, value) == null;   
    }
    
    public int get(String path) {
        return file.getOrDefault(path, -1);
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * boolean param_1 = obj.createPath(path,value);
 * int param_2 = obj.get(path);
 */

=======================================================================================================
method 3:

method:

    - Graph
    - 

stats:

    - 
    - 


class FileSystem {
    
    class Node {
        String name;
        Map<String, Node> contents;
        Integer val;
        
        public Node(String name, int val) {
            this.name = name;
            this.contents = new HashMap();
            this.val = val;
        }
    }

    Node root;
    
    public FileSystem() {
        this.root = new Node("", -1);
    }
    
    public boolean create(String path, int value) {
        if (path.length() <= 1)
            return false;
        
        String[] split = path.split("/");
                
        Node curr = root;
        
        for (int i = 1; i < split.length - 1; i++) {
            if (!curr.contents.containsKey(split[i]))
                return false;
            
            curr = curr.contents.get(split[i]);
        }
        
        String fileName = split[split.length - 1];
        curr.contents.put(fileName, new Node(fileName, value));
        
        return true;
    }
    
    public int get(String path) {        
        String[] split = path.split("/");
                
        Node curr = root;
        
        for (int i = 1; i < split.length; i++) {
            if (!curr.contents.containsKey(split[i]))
                return -1;
            
            curr = curr.contents.get(split[i]);
        }
        
        return curr.val;
    }
}
