Check if any two intervals overlap among a given set of intervals:
[(1,3),(6,9),(2,4),(5,8))]

 Result:  [(1, 4),(5,9)] 
 
 static class Interval {
     int start;
     int end;
     public Interval(int start, int end) {
         this.start = start;
         this.end = end;
     }
 }
 
 static Interval[] checkAndMerge(Interval arr[]){
     ArrayList<Interval> result = new ArrayList<>();
     
     // check edge case
     if (arr.length == 0) return result;
     
     if (arr.length == 1) return result.add(arr[0]);
 
     // step 1: sort
     Arrays.sort(arr, i1, i2 -> Integer.compare(i1.start, i2.start));
     
     // step 2: compare
     for (Interval interval:arr) {
         if(result.isEmpty()||result.getLast().end < interval.start)
                result.add(interval);
                
          else {
              result.getLast().end = Math.max(interval.end, result.getLast().end);
          }
     }
     
     return result;

 }
 
 [(1,3),(2,4),(5,8),(6,9))]
 
 [1,3]
 [2,4]
 
 
 result:{[1,4]}
 --------
 check on the input: 
 1. empty input
 2. input of size 1.
 3. sorted input [] with length > 3
 4. unsorted input []
 
 5. if there are identical intervals
 
 
 
 
 
 --------------
 
 // assume value is the type of V
 // key is the type K
 
 class HashMap{
 
 [0] -> {(0,1)-> (3,3) -> (6,4)}
 [1] -> {2}
 [2] -> {10}
 
     // private final double LOAD_FAC = 0.8;
     private ArrayList<Node> nodes
     
 
     class Node{
         K key;
         V value;
         Node next;
         
         public Node(K key, V value) {
             this.key = key;
             this.value = value;
             this.next = next;
         }
         
     }
     
     // constructor
     // rangeSize = range of value 
     public class HashMap(int rangeSize){
         //nodes = new ArrayList<>();
         Node[] nodes = int[rangeSize];
     }
     
     public V get(K key) {
         int index = hash(key);
         for (Node x = node[index]; x != null; x = x.next) {
             if (x.key == inputKey){
                 x.value = inputVlue;
             
         }
     }
 
     // assume values are non-negatvie
     public void put(K inputKey, V inputValue){
         int idx = hash(key);
         
         // update value if the key already exists
         for (Node x = node[index]; x != null; x = x.next) {
             if (x.key == inputKey){
                 x.value = inputVlue;
                 return;
             }
         }
         
         // if the key doesn't exist, create new node and add to bucket
         nodes[index] = new Node(key, inputValue, nodex[idx]);
     
     }
     
     private V hash(K key) {
         return key % nodes.length;
     }
     
     private void rehash(){
     
     }
 
 
 
 }
 
 
 
 
 
 