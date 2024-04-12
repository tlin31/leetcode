/** 
Given a binary tree, write a function to get the maximum width of the given tree. Width of a tree is maximum of widths of all levels
https://www.geeksforgeeks.org/maximum-width-of-a-binary-tree/
**/

/**
Method 1 (Using Level Order Traversal with Queue)
In this method we store all the child nodes at the current level in the queue and then count the total number of nodes after the level 
order traversal for a particular level is completed. Since the queue now contains all the nodes of the next level, we can easily find 
out the total number of nodes in the next level by finding the size of queue. We then follow the same procedure for the successive levels
. We store and update the maximum number of nodes found at each level.
**/

import java.util.LinkedList; 
import java.util.Queue; 
  
public class maxwidthusingqueue  
{ 
    /* A binary tree node has data, pointer to  
       left child and a pointer to right child */
    static class node  
    { 
        int data; 
        node left, right; 
  
        public node(int data)  
        { 
            this.data = data; 
        } 
    } 
  
    // Function to find the maximum width of  
    // the tree using level order traversal 
    static int maxwidth(node root)  
    { 
        // Base case 
        if (root == null) 
            return 0; 
          
        // Initialize result 
        int maxwidth = 0; 
          
        // Do Level order traversal keeping  
        // track of number of nodes at every level 
        Queue<node> q = new LinkedList<>(); 
        q.add(root); 
        while (!q.isEmpty())  
        { 
            // Get the size of queue when the level order 
            // traversal for one level finishes 
            int count = q.size(); 
              
            // Update the maximum node count value 
            maxwidth = Math.max(maxwidth, count); 
              
            // Iterate for all the nodes in  
            // the queue currently 
            while (count-- > 0)  
            { 
                // Dequeue an node from queue 
                node temp = q.remove(); 
              
                // Enqueue left and right children  
                // of dequeued node 
                if (temp.left != null)  
                { 
                    q.add(temp.left); 
                } 
                if (temp.right != null)  
                { 
                    q.add(temp.right); 
                } 
            } 
        } 
        return maxwidth; 
    } 
  
    public static void main(String[] args)  
    { 
        node root = new node(1); 
        root.left = new node(2); 
        root.right = new node(3); 
        root.left.left = new node(4); 
        root.left.right = new node(5); 
        root.right.right = new node(8); 
        root.right.right.left = new node(6); 
        root.right.right.right = new node(7); 
  
                /*   Constructed Binary tree is: 
                1 
              /   \ 
            2      3 
          /  \      \ 
         4    5      8 
                   /   \ 
                  6     7    */
                    
        System.out.println("Maximum width = " + maxwidth(root)); 
    } 
} 

/**
Method 2 (Using Preorder Traversal)
we create a temporary array count[] of size equal to the height of tree. 
We initialize all values in count as 0. We traverse the tree using preorder traversal and fill the entries in count so that the count 
array contains count of nodes at each level in Binary Tree. (use recursion)
**/
// Java program to calculate width of binary tree 

/* A binary tree node has data, pointer to left child 
and a pointer to right child */
class Node 
{ 
    int data; 
    Node left, right; 

    Node(int item) 
    { 
        data = item; 
        left = right = null; 
    } 
} 

class BinaryTree 
{ 
    Node root; 

    /* Function to get the maximum width of a binary tree*/
    int getMaxWidth(Node node) 
    { 
        int width; 
        int h = height(node); 

        // Create an array that will store count of nodes at each level 
        // can use array list instead to auto size
        int count[] = new int[10]; 

        int level = 0; 

        // Fill the count array using preorder traversal 
        getMaxWidthRecur(node, count, level); 

        // Return the maximum value from count array 
        return getMax(count, h); 
    } 

    // A function that fills count array with count of nodes at every 
    // level of given binary tree 
    // loop through all nodes
    void getMaxWidthRecur(Node node, int count[], int level) 
    { 
        if (node != null) 
        { 
            count[level]++; 
            getMaxWidthRecur(node.left, count, level + 1); 
            getMaxWidthRecur(node.right, count, level + 1); 
        } 
    } 

    /* UTILITY FUNCTIONS */
    
    /* Compute the "height" of a tree -- the number of 
    nodes along the longest path from the root node 
    down to the farthest leaf node.*/
    int height(Node node) 
    { 
        if (node == null) 
            return 0; 
        else
        { 
            /* compute the height of each subtree */
            int lHeight = height(node.left); 
            int rHeight = height(node.right); 
            
            /* use the larger one */
            return (lHeight > rHeight) ? (lHeight + 1) : (rHeight + 1); 
        } 
    } 
    
    // Return the maximum value from count array 
    int getMax(int arr[], int n) 
    { 
        int max = arr[0]; 
        int i; 
        for (i = 0; i < n; i++) 
        { 
            if (arr[i] > max) 
                max = arr[i]; 
        } 
        return max; 
    } 

    /* Driver program to test above functions */
    public static void main(String args[]) 
    { 
        BinaryTree tree = new BinaryTree(); 
        
        /* 
        Constructed bunary tree is: 
             1 
            / \ 
            2 3 
           / \ \ 
           4 5 8 
               / \ 
               6 7 */
        tree.root = new Node(1); 
        tree.root.left = new Node(2); 
        tree.root.right = new Node(3); 
        tree.root.left.left = new Node(4); 
        tree.root.left.right = new Node(5); 
        tree.root.right.right = new Node(8); 
        tree.root.right.right.left = new Node(6); 
        tree.root.right.right.right = new Node(7); 

        System.out.println("Maximum width is " + 
                        tree.getMaxWidth(tree.root)); 
    } 
} 

// This code has been contributed by Mayank Jaiswal 

