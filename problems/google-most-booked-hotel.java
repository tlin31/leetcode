Google | OA 2019 | Most Booked Hotel Room


Given a hotel which has 10 floors [0-9] and each floor has 26 rooms [A-Z]. You are given a sequence of 
rooms, where + suggests room is booked, - room is freed. You have to find which room is booked maximum 
number of times.

You may assume that the list describe a correct sequence of bookings in chronological order; that is, 
only free rooms can be booked and only booked rooms can be freeed. All rooms are initially free. Note 
that this does not mean that all rooms have to be free at the end. In case, 2 rooms have been booked 
the same number of times, return the lexographically smaller room.

You may assume:

1. N (length of input) is an integer within the range [1, 600]
2. each element of array A is a string consisting of three characters: "+" or "-"; a digit "0"-"9"; and 
uppercase English letter "A" - "Z"
3. the sequence is correct. That is every booked room was previously free and every freed room was previously 
booked.


Example:

Input: ["+1A", "+3E", "-1A", "+4F", "+1A", "-3E"]
Output: "1A"
Explanation: 1A as it has been booked 2 times.

=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- 

// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        String[] test={"+1A", "+3E", "-1A", "+4F", "+1A", "-3E"};
        String[] test1={"+1A", "+3E", "-1A", "+4F", "+1A", "-3E", "+3E", "-3E"};
        String[] test2={"+1A", "+1B", "-1A", "+4F", "-1B"};
        String[] test3={"+3A", "+1B", "-3A", "+4F", "-1B"};
        mostBookedHotelRoom(test);
        mostBookedHotelRoom(test1);
        mostBookedHotelRoom(test2);
        mostBookedHotelRoom(test3);
    }
    
    public static void mostBookedHotelRoom(String[] bookings){
        int maxCount = 0;
        HashMap<String, Integer> countMap=new HashMap<>();
        String mostBookRoom = "A";


        for(String booked:bookings){
        	boolean occupied =(booked.substring(0,1).equals("+"))? true: false;
            String room = booked.substring(1);
            if(countMap.containsKey(room)){
                if(occupied) 
                	countMap.put(room, countMap.get(room)+1);
            }else{
            	// encounter a new room
                countMap.put(room, 1);
            }
        }
        
        for(String room:countMap.keySet()){
        	int curCount = countMap.get(room);
            if(maxCount < curCount) {
	            mostBookRoom = room;
	            maxCount = curCount;

            }else if(maxCount == curCount && (room.charAt(room.length()-1) < mostBookRoom.charAt(room.length()-1))){
            	mostBookRoom = room;
	           	maxCount = curCount;
	            
            }
        }
        
        
       return mostBookRoom;
        
    }
}