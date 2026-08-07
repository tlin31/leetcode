853. Car Fleet - Medium

There are n cars at given miles away from the starting mile 0, traveling to reach the mile target.

You are given two integer arrays position and speed, both of length n, where position[i] is the starting mile of the ith car and speed[i] is the speed of the ith car in miles per hour.

A car cannot pass another car, but it can catch up and then travel next to it at the speed of the slower car.

A car fleet is a single car or a group of cars driving next to each other. The speed of the car fleet is the minimum speed of any car in the fleet.

If a car catches up to a car fleet at the mile target, it will still be considered as part of the car fleet.

Return the number of car fleets that will arrive at the destination.

 

Example 1:

Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]

Output: 3

Explanation:

The cars starting at 10 (speed 2) and 8 (speed 4) become a fleet, meeting each other at 12. The fleet forms at target.
The car starting at 0 (speed 1) does not catch up to any other car, so it is a fleet by itself.
The cars starting at 5 (speed 1) and 3 (speed 3) become a fleet, meeting each other at 6. The fleet moves at speed 1 until it reaches target.
Example 2:

Input: target = 10, position = [3], speed = [3]

Output: 1

Explanation:

There is only one car, hence there is only one fleet.
Example 3:

Input: target = 100, position = [0,2,4], speed = [4,2,1]

Output: 1

Explanation:

The cars starting at 0 (speed 4) and 2 (speed 2) become a fleet, meeting each other at 4. The car starting at 4 (speed 1) travels to 5.
Then, the fleet at 4 (speed 2) and the car at position 5 (speed 1) become one fleet, meeting each other at 6. The fleet moves at speed 1 until it reaches target.
 

Constraints:

n == position.length == speed.length
1 <= n <= 105
0 < target <= 106
0 <= position[i] < target
All the values of position are unique.
0 < speed[i] <= 106


******************************************************
key:
	- 
	- edge case:
		1) 
		2)

******************************************************



===================================================================================================
Method 1:

Method:

Key insight: a car can only catch the car directly ahead of it on a one-lane road — it can't pass. So we only need to compare each car to the nearest car in front of it.

Two observations:

1. Sort by position descending — process cars from closest to target first
2. Compute time to target: time = (target - position) / speed
	If current car's time ≤ car ahead's time → it catches up → same fleet (don't push)
	If current car's time > car ahead's time → it can never catch up → new fleet (push)

Why monotonic stack?
After sorting by position descending, the stack maintains times in increasing order from bottom to top — each new fleet that forms has a longer arrival time than all fleets ahead of it. When a car catches up (time ≤ stack top), it joins the existing fleet. The stack size at the end = number of fleets.



Stats:

Time Sort + monotonic stack --> O(n log n)
Space O(n)


class Solution:
    def carFleet(self, target: int, position: List[int], speed: List[int]) -> int:
        # Pair and sort by position descending
        cars = sorted(zip(position, speed), reverse = True) 
        stack = []

        for pos, speed in cars:
            time = (target-pos)/speed
            if not stack or time>stack[-1]:
                stack.append(time)
            
            # otherwise, join the fleet
        
        return len(stack)


class Solution {
    public int carFleet(int target, int[] position, int[] speed) {
        //check edge cases with position and speed

        int n = position.length;

        // Create and sort by position descending
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> position[b] - position[a]);

        Deque<Double> stack = new ArrayDeque<>();

        for (int idx : indices) {
            double time = (double)(target - position[idx]) / speed[idx];

            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
            // else: joins the fleet ahead, don't push
        }
        return stack.size();        
    }
}

例子：
target=12
position=[10,8,0,5,3], speed=[2,4,1,1,3]

Sort by position desc: [(10,2),(8,4),(5,1),(3,3),(0,1)]
Compute times:
  (10,2) → (12-10)/2 = 1.0
  (8,4)  → (12-8)/4  = 1.0
  (5,1)  → (12-5)/1  = 7.0
  (3,3)  → (12-3)/3  = 3.0
  (0,1)  → (12-0)/1  = 12.0

Process:
  time=1.0: stack=[] → push → stack=[1.0]
  time=1.0: 1.0 <= stack top 1.0 → joins fleet, skip
  time=7.0: 7.0 > stack top 1.0 → new fleet → stack=[1.0, 7.0]
  time=3.0: 3.0 <= stack top 7.0 → joins fleet, skip
  time=12.0: 12.0 > stack top 7.0 → new fleet → stack=[1.0, 7.0, 12.0]

→ return len(stack) = 3 ✅



===================================================================================================


Method  2
 the "stack" is really just tracking the count of distinct fleet times. We could simplify to just a counter:
 
pythondef carFleet(target, position, speed) -> int:
    pairs = sorted(zip(position, speed), reverse=True)
    fleets = 0
    max_time = 0  # time of the leading fleet

    for pos, spd in pairs:
        time = (target - pos) / spd
        if time > max_time:
            fleets += 1
            max_time = time
    return fleets
