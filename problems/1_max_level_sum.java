/* Iterative
Root is at level 0, push root node to queue.
Create variable to store maxSum & level.
Push null as level delimiter, to the queue.  // or use the while loop with count.
Iterate through the Queue
Calculate the local sum
Pop node from queue
If popped up node is null,
 We are at next level.
Update maxSum if it is less than local sum.
Save the level of local sum.
Add next level children (left or/and right nodes)
Once we exit the loop, we will get the maximum sum and level.

*/

int maxLevelSum(struct Node * root) 
{ 
    // Base case 
    if (root == NULL) 
        return 0; 
  
    // Initialize result 
    int result = root->data; 
  
    // Do Level order traversal keeping track of number of nodes at every level. 
    queue<Node*> q; 
    q.push(root); 
    while (!q.empty()) 
    { 
        // Get the size of queue when the level order 
        // traversal for one level finishes 
        int count = q.size() ; 
  
        // Iterate for all the nodes in the queue currently 
        int sum = 0; 
        while (count--) 
        { 
            // Dequeue an node from queue 
            Node *temp = q.front(); 
            q.pop(); 
  
            // Add this node's value to current sum. 
            sum = sum + temp->data; 
  
            // Enqueue left and right children of 
            // dequeued node 
            if (temp->left != NULL) 
                q.push(temp->left); 
            if (temp->right != NULL) 
                q.push(temp->right); 
        } 
  
        // Update the maximum node count value 
        result = max(sum, result); 
    } 
  
    return result; 
} 

Recursion: 

findSum(queue, max)