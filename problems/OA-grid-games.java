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

    /*
     * Complete the 'gridGame' function below.
     *
     * The function is expected to return a 2D_INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY grid
     *  2. INTEGER k
     *  3. STRING_ARRAY rules
     */

    private static int[] rows = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
    private static int[] column = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};

    public static List<List<Integer>> gridGame(List<List<Integer>> grid, int k, List<String> rules) {
        int[] numRules = new int[rules.size()];
        for(int i = 0; i < numRules.length; i++){
            if(rules.get(i).equals("alive")){
                numRules[i] = 1;
            }else{
                numRules[i] = 0;
            }
        }

        for(int i = 0; i < k; i++){
            update(grid, numRules);
        }

        return grid;
    }

    private static void update(List<List<Integer>> grid, int[] rules){
        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(0).size(); j++){
                int numNeighbors = getNeighbors(grid, rules, i, j);

                if(rules[numNeighbors] == 0){ 
                    int temp = grid.get(i).get(j) == 0 ? 0 : 2;
                    grid.get(i).set(j, temp);
                }else{ 
                    int temp = grid.get(i).get(j) == 1 ? 1 : 3;
                    grid.get(i).set(j, temp);
                }
            }
        }

        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(0).size(); j++){
                    int temp = grid.get(i).get(j)%2;
                    grid.get(i).set(j, temp);            
            }
        }
    }

    private static int getNeighbors(List<List<Integer>> grid, int[] rules, int x, int y){
        int count = 0;
        for(int i = 0; i < column.length; i++){
            if(y + rows[i] < 0||  y + rows[i] >= grid.get(0).size() || x + column[i] < 0 || x + column[i] >= grid.size() ){
                continue;
            }

            if(grid.get(x + column[i]).get(y + rows[i]) == 1 || grid.get(x + column[i]).get(y + rows[i]) == 2){
                count++;
            }
        }

        return count;
    }

}
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int gridRows = Integer.parseInt(bufferedReader.readLine().trim());
        int gridColumns = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> grid = new ArrayList<>();

        IntStream.range(0, gridRows).forEach(i -> {
            try {
                grid.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        int rulesCount = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> rules = IntStream.range(0, rulesCount).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .collect(toList());

        List<List<Integer>> result = Result.gridGame(grid, k, rules);

        result.stream()
            .map(
                r -> r.stream()
                    .map(Object::toString)
                    .collect(joining(" "))
            )
            .map(r -> r + "\n")
            .collect(toList())
            .forEach(e -> {
                try {
                    bufferedWriter.write(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
