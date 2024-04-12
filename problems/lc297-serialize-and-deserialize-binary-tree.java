297. Serialize and Deserialize Binary Tree - Hard

Serialization is the process of converting a data structure or object into a sequence of bits so that it 
can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed 
later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your 
serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be 
serialized to a string and this string can be deserialized to the original tree structure.

Example: 

You may serialize the following tree:

    1
   / \
  2   3
     / \
    4   5

as "[1,2,3,null,null,4,5]"
Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily 
need to follow this format, so please be creative and come up with different approaches yourself.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize 
algorithms should be stateless.


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        
    }
}


******************************************************
key:
	- pre-order, dfs, bfs
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- print the tree in pre-order traversal and use "X" to denote null node and split node with ",". 
	- StringBuilder
	- For deserializing, we use a Queue to store the pre-order traversal and since we have "X" as null node, 
	  we know exactly how to where to end building subtress.
	- 

stats:

	- dfs
	- 

public class Codec {
    private static final String spliter = ",";
    private static final String NN = "X";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    private void buildString(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append(NN).append(spliter);
        } else {
            sb.append(node.val).append(spliter);
            buildString(node.left, sb);
            buildString(node.right,sb);
        }
    }
    
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Deque<String> nodes = new LinkedList<>();
        nodes.addAll(Arrays.asList(data.split(spliter)));
        return buildTree(nodes);
    }
    
    private TreeNode buildTree(Deque<String> nodes) {
        String val = nodes.remove();
        if (val.equals(NN)) return null;
        else {
            TreeNode node = new TreeNode(Integer.valueOf(val));
            node.left = buildTree(nodes);
            node.right = buildTree(nodes);
            return node;
        }
    }
}

----------
public class Codec {

    // Encodes a tree to a single string.
    /*
      序列化的思想是利用dfs的preorder遍历得到一个完整地序列，遇到null的地方可以直接存储null即可
      反序列化的思想是利用list这个结构，每次都返回list的首元素，作为上一个节点的左节点和右节点，然后依次删除掉list的首元素
    */
    public String serialize(TreeNode root) {
        return serializeHelper(root,"");
    }
    
    private String serializeHelper(TreeNode root, String str){
        if (root == null){
            str +="null,";
            return str;
        }
        // preorder add node
        str += root.val + ",";
        str = serializeHelper(root.left, str);
        str = serializeHelper(root.right, str);
        
        return str;
        
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] data_ = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(data_));
        return desHelper(list);
    }
    
    private TreeNode desHelper(List<String> list){
        if (list.get(0).equals("null")){
            list.remove(0);
            return null;
        }
        
        int val = Integer.valueOf(list.get(0));
        TreeNode root = new TreeNode(val);
        list.remove(0);
        
        root.left = desHelper(list);
        root.right = desHelper(list);
        
        return root; 
    }
}
=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- Runtime: 178 ms, faster than 10.91% of Java online submissions for Serialize and Deserialize Binary Tree.
	- Memory Usage: 39.6 MB, less than 40.00% 




public String serialize(TreeNode root) 
{
    if(root == null) return "#";
    
    return "" + root.val + " " + serialize(root.left) + " " + serialize(root.right);
}


// Decodes your encoded data to tree.
public TreeNode deserialize(String data) 
{
    return build(new Scanner(data));
}

private TreeNode build(Scanner sc)
{
    if(!sc.hasNext()) return null;
    String tk = sc.next();
    if(tk.equals("#")) return null;
    
    TreeNode root = new TreeNode(Integer.parseInt(tk));
    root.left = build(sc);
    root.right = build(sc);
    
    return root;
}
=======================================================================================================
method 3:

method:

	- Here I use typical BFS method to handle a binary tree. I use string n to represent null values. 
	  The string of the binary tree in the example will be "1 2 3 n n 4 5 n n n n ".
	- When deserialize the string, I assign left and right child for each not-null node, and add 
	  the not-null children to the queue, waiting to be handled later.

stats:

	- Runtime: 14 ms, faster than 43.74% of Java online submissions for Serialize and Deserialize Binary Tree.
	- Memory Usage: 40.1 MB, less than 31.43% 



public class Codec {
    public String serialize(TreeNode root) {
        if (root == null) return "";
        Queue<TreeNode> q = new LinkedList<>();
        StringBuilder res = new StringBuilder();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode node = q.poll();
            if (node == null) {
                res.append("n ");
                continue;
            }
            res.append(node.val + " ");
            q.add(node.left);
            q.add(node.right);
        }
        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == "") return null;
        Queue<TreeNode> q = new LinkedList<>();
        String[] values = data.split(" ");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        q.add(root);
        for (int i = 1; i < values.length; i++) {
            TreeNode parent = q.poll();
            if (!values[i].equals("n")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                q.add(left);
            }
            if (!values[++i].equals("n")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                q.add(right);
            }
        }
        return root;
    }
}



