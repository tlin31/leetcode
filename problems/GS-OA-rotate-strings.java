!! important is to % length

method:
    -   reverse(array, 0, input.length() - 1);
        reverse(array, 0, steps - 1);
        reverse(array, steps, array.length - 1);
    -   ex. (1,2,3,4,5 & step = 3)
        1st reverse: 5,4,3,2,1
        2nd reverse: 3,4,5,2,1
        3rd reverse: 3,4,5,1,2


package OA.GoldmanSachs;

import java.util.Arrays;
import java.util.List;

public class RotateString {

    public static String rotate(String input, List<Integer> directions, List<Integer> amounts){
        if(input == null || input.length() <= 1){
            return input;
        }

        if(directions == null || directions.size() == 0){
            return input;
        }

        int step = 0;

        for(int i = 0; i < directions.size(); i++){
            int dir = directions.get(i) == 1 ? 1 : -1; // left -1, right 1
            step += (dir * amounts.get(i)) % input.length();
           
        }
        step %= input.length();
        if(step < 0){
            step += input.length();
        }
        return rightShift(input, step);
    }

    private static String rightShift(String input, int steps){
        char[] array = input.toCharArray();


        reverse(array, 0, input.length() - 1);
        reverse(array, 0, steps - 1);
        reverse(array, steps, array.length - 1);

        return new String(array);
    }

    private static void reverse(char[] array, int left, int right){
        while(left < right){
            swap(array, left++, right--);
        }
    }

    private static void swap(char[] array, int left, int right){
        char temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    public static void main(String[] args){
        String input = "helloworld";
        List<Integer> directions = Arrays.asList(0, 1);
        List<Integer> amounts = Arrays.asList(101, 105);

        System.out.println(rotate(input, directions, amounts));
    }
}
