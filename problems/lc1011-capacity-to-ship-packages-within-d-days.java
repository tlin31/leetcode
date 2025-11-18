1011. Capacity To Ship Packages Within D Days - Medium

A conveyor belt has packages that must be shipped from one port to another within D days.

The i-th package on the conveyor belt has a weight of weights[i].  Each day, we load the ship 
with packages on the conveyor belt (in the order given by weights). We may not load more weight 
than the maximum weight capacity of the ship.

Return the least weight capacity of the ship that will result in all the packages on the conveyor 
belt being shipped within D days.

 

Example 1:

Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
Output: 15
Explanation: 
A ship capacity of 15 is the minimum to ship all the packages in 5 days like this:
1st day: 1, 2, 3, 4, 5
2nd day: 6, 7
3rd day: 8
4th day: 9
5th day: 10

Note that the cargo must be shipped in the order given, so using a ship of capacity 14 and 
splitting the packages into parts like (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) is not allowed. 


Example 2:
Input: weights = [3,2,2,4,1,4], D = 3
Output: 6
Explanation: 
A ship capacity of 6 is the minimum to ship all the packages in 3 days like this:
1st day: 3, 2
2nd day: 2, 4
3rd day: 1, 4


Example 3:
Input: weights = [1,2,3,1,1], D = 4
Output: 3
Explanation: 
1st day: 1
2nd day: 2
3rd day: 3
4th day: 1, 1
 

Note:

1 <= D <= weights.length <= 50000
1 <= weights[i] <= 500

******************************************************
key:
	- binary search on weight
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- max = total weight, because the no of days given can be 1. call it end.

	  min =  maximum of all weights, because we may have to carry that particular load on a single day.

	  Then simple binary search can be applied as we have both max and min weights. Total days can be 
	  calculated based on the value of mid ( (beg+end)/2 ).

	  If number of days required are <= D days given, then we can end = mid-1. Update ans equal to mid.
 	  else beg = mid+1.
	- 

stats:

	- Runtime: 6 ms, faster than 98.19% 
	- Memory Usage: 43.1 MB, less than 69.23% 


    public int shipWithinDays(int[] weights, int D) {
        int beg=0;
        int end=0;

        for(int w:weights){
            beg = Math.max(beg,w);
            end +=w;
        }

        int ans = 0;
        while(beg <= end){
            int mid = beg + (end-beg)/2;
            int days = findDays(weights,mid);

            // with current mid, use less days then required, can further reduce capacity
            if(days <= D){
                end=mid-1;
                ans = mid;
            }
            else{
                beg=mid+1;
            }
        }
        return ans;
    }
 
    public int findDays(int a[],int capacity){
        int days=1;
        int sum=0;
        for(int x:a){
            sum=sum+x;
            //一天之内装不完，days++ 作为新的单独一天
            if(sum > capacity){
                days++;
                sum=x;
            }
        }
        return days;
    }
    



=======================================================================================================
method 2:

method:

	- Time complex: O(log(sum(weights)) * log(len(weights)) )

汉语:
使用了两次二分法.
第一次在"寻找负重上限"的时候
第二次在"基于假定的负重上限, 对货物进行分组"的时候.

English:
Binary Search are used two times.
The first time is when looking for capacity.
The second time is when grouping the packages according to the assumed capacity.
	- 

stats:

	- 
	- 

from bisect import bisect_right
class Solution:
    def shipWithinDays(self, weights: List[int], D: int) -> int:
        def check(mid):
            last = 0
            for i in range(D):
                cur = last + mid
                idx = bisect_right(weights, cur) - 1
                last = weights[idx]
                if idx == len(weights) - 1:
                    return True
            return False
            
        left = max(max(weights), sum(weights)//D)
        right = sum(weights)
        for i in range(1, len(weights)):
            weights[i] += weights[i - 1]
        while left < right:
            mid = (left + right) >> 1
            if check(mid):
                right = mid
            else:
                left = mid + 1
        return right

class Solution:
    
    def createShipmentOrder(self, weights: List[int], ship_weight: int) -> List[int]:
        shipments = 1 
        weight_so_far = 0
        for w in weights:
            # If space left on the shipment, add more items
            if (weight_so_far + w <= ship_weight):
                weight_so_far = weight_so_far + w
            # Otherwise, add a new shipment and load overweight item on a new shipment
            else:
                weight_so_far = w 
                shipments = shipments + 1
        return shipments       
    

    def shipWithinDays(self, weights: List[int], D: int) -> int:
        if len(weights) == D:
            return max(weights)
        
        # Minimum possible weight for the ship is the max weight of 1 package
        min_weight = max(weights) 
        # Maximum possible weight for the ship is the sum of all weights
        max_weight = sum(weights)
        
        # Binary search
        while min_weight < max_weight:
            # Calculate binary search mid point weight to test
            mid_weight = min_weight + (max_weight - min_weight) // 2
            shipments = self.createShipmentOrder(weights, mid_weight)
            # Ship weight is too big for the items on the belt
            if shipments <= D:
                max_weight = mid_weight
            # Ship weight is too small for the items on the belt
            else: 
                min_weight = mid_weight + 1
        return min_weight 
