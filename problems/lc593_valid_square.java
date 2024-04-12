593. Valid Square

Given the coordinates of four points in 2D space, return whether the four points could construct a square.

The coordinate (x,y) of a point is represented by an integer array with two integers.

Example:

Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
Output: True
 

Note:

All the input integers are in the range [-10000, 10000].
A valid square has four equal sides with positive length and four equal angles (90-degree angles).
Input points have no order.

******************************************************
key:
    - Math
    - edge case:
        1) empty string, return empty
        2)

******************************************************



=======================================================================================================
method 1:

method:
    In each case, after sorting, we obtain the following conclusion regarding the connections of the 
    points:

        1. p0p1, p0p2, p1p3, p2p3 form the four sides of any valid square.
        2. p0p3, p1p2 form the diagonals of the square.


stats:

    - 2ms, beats 49%
    - 


class Solution {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        HashSet<Integer> hs = new HashSet<>(Arrays.asList(dis(p1, p2), dis(p1, p3), dis(p1, p4), 
                                            dis(p2, p3), dis(p2, p4), dis(p3, p4)));        
        //One each for side & diagonal
        return !hs.contains(0) && hs.size()==2; 
    }

    // exact distance, 三角形斜边的平方
    int dis(int[] a, int[] b){
	    return (a[0]-b[0])*(a[0]-b[0]) + (a[1]-b[1])*(a[1]-b[1]);
    }
    
}


=======================================================================================================
method 2:

Method:
    if a quadrilateral is a parallelogram, then it should satisfy the following:
        (x1+x3 == x2+x4) && (y1+y3 == y2+y4), where xi and yi is the x-y coordinate of point i on 
        2D plane, and Point1 and Point 3 (Or Point 2 and Point 4) are on the diagonal

    We also learned that if a parallelogram is a square, its four sides must be equl length and the 
        adjacent sides must be perpendicular. We can use vectors to easily decide this:
            1. If vector a and vector b are verticle, then their dot product equals 0.
            2. Use the distance formula to calculate the length of the vector


Stats:

    - 0ms, beats 100 % 
    - 
	
	We learned from high school that if a quadrilateral is a parallelogram, then it should satisfy the following:
	(x1+x3 == x2+x4) && (y1+y3 == y2+y4), where xi and yi is the x-y coordinate of point i on 2D plane, and Point1 and Point 3 (Or Point 2 and Point 4) are on the diagonal

	We also learned that if a parallelogram is a square, its four sides must be equl length and the adjacent sides must be perpendicular. We can use vectors to easily decide this:
	If vector a and vector b are verticle, then their dot product equals 0.
Use the distance formula to calculate the length of the vector

class Solution {
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        if( (p1[0]==p2[0] && p1[1]==p2[1]) ||  (p1[0]==p3[0] && p1[1]==p3[1]) || (p1[0]==p4[0] && p1[1]==p4[1]) || (p3[0]==p2[0] && p3[1]==p2[1]) || (p4[0]==p2[0] && p4[1]==p2[1]) || (p3[0]==p4[0] && p3[1]==p4[1]))// in case any two of the points are the same
            return false;
        if(vector(p1,p2,p3,p4)){//p1 and p2 diagonal
            return true;
        }
        if(vector(p1,p3,p2,p4)){//p1 and p2 diagonal
            return true;
        }
        if(vector(p1,p4,p2,p3)){//p1 and p2 diagonal
            return true;
        }
        return false;
    }

    public boolean vector(int []p1,int []p2,int []p3,int []p4){
        if(p1[0]+p2[0] == p3[0]+p4[0] && p1[1]+p2[1] == p3[1]+p4[1]){//if it's a parallelogram
            int []vecA={p3[0]-p1[0],p3[1]-p1[1]};
            int []vecB={p1[0]-p4[0],p1[1]-p4[1]};
            if(vecA[0]*vecB[0]+vecA[1]*vecB[1] == 0 && vecA[0]*vecA[0]+vecA[1]*vecA[1] == vecB[0]*vecB[0]+vecB[1]*vecB[1]){
                return true;
            }
        }
        return false;
    }
}

// 	python, sorting solution
// 	16ms, beats 92.83%

class Solution(object):
    def validSquare(self, p1, p2, p3, p4):
        if p1 == p2 == p3 == p4: return False

        p1,p2,p3,p4 = sorted([p1,p2,p3,p4])
        if p2[1] < p3[1]: p2,p3 = p3,p2

        return p3 == [p1[0] + (p2[1]-p1[1]), p1[1] - (p2[0]-p1[0])]\
           and p4 == [p2[0] + (p2[1]-p1[1]), p2[1] - (p2[0]-p1[0])]
