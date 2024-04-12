Google-block-with-min-distance (// https://leetcode.com/discuss/interview-question/285144/Google-interview-question-Minimize-the-distance-to-the-farthest-point)

Assume you are looking to move, and have a set of amenities that you want to have easy access to 
from your new home. You have found a neighborhood you like, each block of which has zero or more 
amenities. 

Question:
How would you pick the block to live in such that the farthest distance to any amenity in your 
list is minimized?

Example:
Say your list contains {school, grocery}, and the blocks are as follows:
1: restaurant, grocery
2: movie theater
3: school
4:
5: school

The ideal choice would be block 2, such that the distances to the grocery and the nearest school are 
1 each. Living on block 1 or 3 would make one of the distances zero, but the other one 2.



=======================================================================================================
method 1:

method:

    - in the map, store (amenity, block_index)
    - use map to only store needed ones.
    - assumption: the required amenities are unique (ex. will not have (school,school))

stats:

    - Time complexity: O(b * n), where b is the number of blocks and n is the average 
                        number of amenities in a block.
    - Space complexity: O(a), where a is the number of amenities.


public class Main {
    
    public int pickBlock(Set<String> amenities, List<Set<String>> blocks) {
        int block = 0;
        int minLen = Integer.MAX_VALUE;
        Map<String, Integer> window = new HashMap<>();
        for (int lo = 0, hi = 0; hi < blocks.size(); hi++) {

            // add matching possible blocks to map
            addBlockToWindow(blocks.get(hi), amenities, window);

            // can do binary search ? or iterate thru all possibilities.
            for (; window.size() == amenities.size(); lo++) {

                // original version:
                // int len = hi - lo;
                // if (len < minLen) {
                //     minLen = len;
                //     block = (lo + hi) >>> 1;
                // }


                // weighted distance, after store (amenity, block_index) into the map
                int totalDist = sum of (all value in the window.map)
                int avgBlock = (totalDist - lo * size of all amenties)/(size of needed amenities) + lo;
                removeBlockFromWindow(blocks.get(lo), amenities, window);
            }
        }
        return block;
    }

    private void addBlockToWindow(Set<String> block, Set<String> amenities, Map<String, Integer> window) {
        for (String amenity : block) {
            if (amenities.contains(amenity)) {
                window.put(amenity, window.getOrDefault(amenity, 0) + 1);
            }
        }
    }

    private void removeBlockFromWindow(Set<String> block, Set<String> amenities, Map<String, Integer> window) {
        for (String amenity : block) {
            if (amenities.contains(amenity)) {
                window.put(amenity, window.get(amenity) - 1);
                window.remove(amenity, 0);
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}