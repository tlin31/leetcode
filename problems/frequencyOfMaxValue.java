
ex. [5,4,5,3,2] query = [1,2,3,4,5]

result = [2,1,1,1,1]
return array = num of items with maximum price between query index and highest index of the price array



import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;


class Result {

    public static List<Integer> frequencyOfMaxValue(List<Integer> price, List<Integer> query) {
        int n = price.size();
        int[] memo = new int[n];
        ArrayList<Integer> result = new ArrayList<>();
        int max = Integer.MIN_VALUE;

        // go from the back
        for(int i = n-1; i >= 0; i--) {
            if (price.get(i)>max){
                max = price.get(i);
                memo[i] = 1;
            } else if (price.get(i) == max) {
                memo[i] = memo[i+1] +1;
            } else {

                // when current price is not the max, it will not update max, just store 
                // the number next to it
                memo[i] = memo[i+1];
            }
        }

        for (Integer start : query){
            result.add(memo[start-1]);
        }
        return result;
    }

}
public class Solution {