855. Exam Room - Medium

In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.

When a student enters the room, they must sit in the seat that maximizes the distance to the closest 
person.  If there are multiple such seats, they sit in the seat with the lowest number.  (Also, if no one 
is in the room, then the student sits at seat number 0.)

Return a 'class' ExamRoom(int N) that exposes two functions: ExamRoom.seat() returning an int representing 
what seat the student sat in, and ExamRoom.leave(int p) representing that the student in seat number p now
leaves the room.  It is guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p.

 

Example 1:

Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
Output: [null,0,9,4,2,null,5]
Explanation:
ExamRoom(10) -> null
seat() -> 0, no one is in the room, then the student sits at seat number 0.
seat() -> 9, the student sits at the last seat number 9.
seat() -> 4, the student sits at the last seat number 4.
seat() -> 2, the student sits at the last seat number 2.
leave(4) -> null
seat() -> 5, the student sits at the last seat number 5.
​​​​​​​

Note:

1 <= N <= 10^9
ExamRoom.seat() and ExamRoom.leave() will be called at most 10^4 times across all test cases.
Calls to ExamRoom.leave(p) are guaranteed to have a student currently sitting in seat number p.

******************************************************
key:
	- list, priority queue, treemap
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Use a list L to record the index of seats where people sit.

        seat():
        1. find the biggest distance at the start, at the end and in the middle.
        2. insert index of seat
        3. return index

        leave(p): pop out p

	- distance function = low + (high-low)/2


stats:

	- O(N) for seat() and leave()
	- 

	int N;
    ArrayList<Integer> L = new ArrayList<>();
    public ExamRoom(int n) {
        N = n;
    }

    public int seat() {
        // empty room, student sits at desk 0
        if (L.size() == 0) {
            L.add(0);
            return 0;
        }

        // d = 最靠墙边的
        int d = Math.max(L.get(0), N - 1 - L.get(L.size() - 1));

        // go through all intervals.
        for (int i = 0; i < L.size() - 1; ++i) 
            d = Math.max(d, (L.get(i + 1) - L.get(i)) / 2);

        if (L.get(0) == d) {
            L.add(0, 0);
            return 0;
        }

        for (int i = 0; i < L.size() - 1; ++i)
            if ((L.get(i + 1) - L.get(i)) / 2 == d) {
                L.add(i + 1, (L.get(i + 1) + L.get(i)) / 2);
                return L.get(i + 1);
            }
        L.add(N - 1);
        return N - 1;
    }

    public void leave(int p) {
        for (int i = 0; i < L.size(); ++i) 
            if (L.get(i) == p) 
                L.remove(i);
    }

========================================
class ExamRoom {
    List<Integer> seat;
    int start =0;
    int end;

    public ExamRoom(int N) {
        this.end = N-1;
        seat = new LinkedList<Integer>();
    }
    
    public int seat() {
        if(seat.size() == 0) {
            seat.add(start); 
            return start;
        }


        int max = Integer.MIN_VALUE;
        int pick = -1;
        int index = -1;

        // if no one sits at 0, then need to consider wall/boundry
        if(seat.get(0)!=0){
            pick = 0;
            max = seat.get(0)-0;
            index = 0;
        }
        
        // main function!!! 
        int l =0, r = 0;
        for(int i=0; i< seat.size();i++){
            if(i == 0 ){
                l = seat.get(0); 
                continue;
            }
            else 
                l = r;
            r = seat.get(i);
            int mid = (l+r)/2;
            int dt = mid-l;
            if(dt>max) {
                max = dt; 
                pick = mid;
                index = i;
            }
        }

        // for the end seat, need to consider with the right wall
        if(seat.get(seat.size()-1)<end){
            l = seat.get(seat.size()-1);
            r = end;
            int dt = r-l;
            if(dt>max) {
                max = dt; 
                pick = r;
                index = seat.size();
            }             
        }

        seat.add(index,pick);
        return pick;
    }
    
    public void leave(int p) {
       for(int i =0 ; i < seat.size();i++){
           if(seat.get(i) == p) {
               seat.remove(i);
               return;
           }
       }
    }
}

=======================================================================================================
method 2:

method:

	- treemap
	- The major idea is to store intervals into the TreeMap, every time when we add a seat, we get the 
      longest interval and put a seat in the middle, then break the interval into two parts. 
    - Notice that for intervals with odd length, we simply trim it into even length using len -= len & 1.


stats:

	- 
	- 

class ExamRoom {

    TreeMap<Integer, TreeSet<Integer>> map = new TreeMap<>();
    TreeSet<Integer> set = new TreeSet<>();
    int N;
    
    public ExamRoom(int N) {
        this.N = N;
    }
    
    public int seat() {
        int res = 0;
        if (set.size() == 0) {
            res = 0;
        } else {
            int first = set.first(), last = set.last();
            Integer max = map.isEmpty() ? null : map.lastKey();
            if (max == null || first >= max / 2 || N - 1 - last > max / 2) {
                if (first >= N - 1 - last) {
                    addInterval(0, first);
                    res = 0;
                } else {
                    addInterval(last, N - 1 - last);
                    res = N - 1;
                }
            } else {
                TreeSet<Integer> temp = map.get(max);
                int index = temp.first();
                int next = set.higher(index);
                int mid = (next + index) / 2;
                removeInterval(index, max);
                addInterval(index, mid - index);
                addInterval(mid, next - mid);
                res = mid;
            }
        }
        set.add(res);
        return res;
    }
		
    public void leave(int p) {
        Integer pre = set.lower(p);
        Integer next = set.higher(p);
        set.remove(p);
        if (next != null) {
            removeInterval(p, next - p);
        }
        if (pre != null) {
            removeInterval(pre, p - pre);
            if (next != null) {
                addInterval(pre, next - pre);
            }
        }
    }
		
    private void addInterval(int index, int len) {
        len -= len & 1;  // trim to even number
        map.putIfAbsent(len, new TreeSet<>());
        map.get(len).add(index);
    }
    
    private void removeInterval(int index, int len) {
        len -= len & 1;
        Set<Integer> temp = map.get(len);
        if (temp == null) {
            return;
        }
        temp.remove(index);
        if (temp.size() == 0) {
            map.remove(len);
        }
    }
}

=======================================================================================================
method 3:

method:

	- Tree set
	- We maintain ExamRoom.students, a sorted list (or TreeSet in Java) of positions the students are 
      currently seated in.

    - ExamRoom.leave(p) operation : we will just list.remove (or TreeSet.remove) the student 
      from ExamRoom.students.

    - ExamRoom.seat() : int operation. For each pair of adjacent students i and j, the maximum distance to
      the closest student is d = (j - i) / 2, achieved in the left-most seat i + d. 

      Otherwise, we could also sit in the left-most seat, or the right-most seat.

      Finally, we should handle the case when there are no students separately.

stats:

	- Time Complexity: Each seat operation is O(P), (P  = # ofstudents sitting), as we iterate through 
    every student. Each leave operation is logP in Java.

	- Space Complexity: O(P), the space used to store the positions of each student sitting.


class ExamRoom {
    int N;
    TreeSet<Integer> students;

    public ExamRoom(int N) {
        this.N = N;
        students = new TreeSet();
    }

    public int seat() {
        //Let us determine student, the position of the next student to sit down.
        int student = 0;
        if (students.size() > 0) {
            //Tenatively, dist is the distance to the closest student,
            //which is achieved by sitting in the position 'student'.
            //We start by considering the left-most seat.
            // take care of corner case: when seat 0 is empty
            int dist = students.first();

            Integer prev = null;
            for (Integer s: students) {
                if (prev != null) {
                    //For each pair of adjacent students in positions (prev, s),
                    //d is the distance to the closest student;
                    //achieved at position prev + d.
                    int d = (s - prev) / 2;
                    if (d > dist) {
                        dist = d;
                        student = prev + d;
                    }
                }
                prev = s;
            }

            //Considering the right-most seat.
            //ex. N  = 10, ---> 0   5 6| Then need to check interval from 6 to 9.
            if (N - 1 - students.last() > dist)
                student = N - 1;
        }

        //Add the student to our sorted TreeSet of positions.
        students.add(student);
        return student;
    }

    public void leave(int p) {
        students.remove(p);
    }
}




