Twitter-OA-2019-Partitioning-array

https://leetcode.com/discuss/interview-question/375262/Twitter-or-OA-2019-or-Partitioning-array  



    public static boolean partitionArrayUnique(int[] nums, int k){
        if(nums.length % k != 0){
            return false;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;

        for(int num: nums){
            map.put(num, map.getOrDefault(num, 0) + 1);
            if(map.get(num) > max){
                max = map.get(num);
            }
        }

        return max <= (nums.length / k);
    }