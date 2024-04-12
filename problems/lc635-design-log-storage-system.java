635. Design Log Storage System - Medium


You are given several logs that each log contains a unique id and timestamp. Timestamp is a string 
that has the following format: Year:Month:Day:Hour:Minute:Second, for example, 2017:01:01:23:59:59. 
All domains are zero-padded decimal numbers.

Design a log storage system to implement the following functions:

	1. void Put(int id, string timestamp): Given a log unique id and timestamp, store the log in 
		your storage system.


	2. int[] Retrieve(String start, String end, String granularity): Return the id of logs whose 
			timestamps are within the range from start to end. Start and end all have the same 
			format as timestamp. However, granularity means the time level for consideration. For 
			example, start = "2017:01:01:23:59:59", end = "2017:01:02:23:59:59", granularity = "Day", 
			it means that we need to find the logs within the range from Jan. 1st 2017 to Jan. 2nd 2017.


Example 1:
put(1, "2017:01:01:23:59:59");
put(2, "2017:01:01:22:59:59");
put(3, "2016:01:01:00:00:00");
retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Year"); // return [1,2,3], because you need to 
return all logs within 2016 and 2017.

retrieve("2016:01:01:01:01:01","2017:01:01:23:00:00","Hour"); // return [1,2], because you need to 
return all logs start from 2016:01:01:01 to 2017:01:01:23, where log 3 is left outside the range.

Note:
There will be at most 300 operations of Put or Retrieve.
Year ranges from [2000,2017]. Hour ranges from [00,23].
Output for Retrieve has no order required.



******************************************************
key:
	- 
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
Method 1:


Stats:

	- The TreeMap is maintained internally as a Red-Black(balanced) tree. Thus, the put method takes 
		O(log(n)) time to insert a new entry into the given set of logs. 
		n = number of entries currently present in the given set of logs.

	- The retrieve method takes O(m_start) time to retrieve the logs in the required range. 
	   Determining the granularity takes O(1) time.
	   To find the logs in the required range, we only need to iterate over those elements which 
	   already lie in the required range. Here, m_start refers to the number of entries in the 
	   current set of logs which have a timestamp greater than the current startstart value.


Method:

	-  Given the granularity we can set a lower bound and upper bound of timestamp so we could do 
	   a range query using TreeMap. To get the lower bound we append a suffix of "2000:01:01:00:00:00" 
	   to the prefix of s and to get the upper bound we append a suffix of "2017:12:31:23:59:59" to the 
	   prefix of e.



public class LogSystem {
    private String min, max;
    private HashMap<String, Integer> map;
    private TreeMap<String, LinkedList<Integer>> logs;

    public LogSystem() {
        min = "2000:01:01:00:00:00";
        max = "2017:12:31:23:59:59";
        map = new HashMap<>();

        // value = start index of that granuity
        map.put("Year", 4);
        map.put("Month", 7);
        map.put("Day", 10);
        map.put("Hour", 13);
        map.put("Minute", 16);
        map.put("Second", 19);
        logs = new TreeMap<>();
    }

    public void put(int id, String timestamp) {
        if(!logs.containsKey(timestamp)) 
        	logs.put(timestamp, new LinkedList<>());

        logs.get(timestamp).add(id);
    }

    public List<Integer> retrieve(String s, String e, String gra) {
        int index = map.get(gra);
        String start = s.substring(0, index)+min.substring(index), 
                 end = e.substring(0, index)+max.substring(index);

        NavigableMap<String, LinkedList<Integer>> sub = logs.subMap(start, true, end, true);

        LinkedList<Integer> ans = new LinkedList<>();

        for (Map.Entry<String, LinkedList<Integer>> entry : sub.entrySet()) {
            ans.addAll(entry.getValue());
        }
        return ans;
    }
}









~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

Let focus on the retrieve function. For each granularity, we should consider all timestamps to be 
truncated to that granularity. For example, if the granularity is 'Day', we should truncate the 
timestamp '2017:07:02:08:30:12' to be '2017:07:02'. Now for each log, if the truncated timetuple 
cur is between start and end, then we should add the id of that log into our answer.


class_LogSystem(object):
    def __init__(self):
        self.logs = []

    def put(self, tid, timestamp):
        self.logs.append((tid, timestamp))
        
    def retrieve(self, s, e, gra):
        index = {'Year': 5, 'Month': 8, 'Day': 11, 
                 'Hour': 14, 'Minute': 17, 'Second': 20}[gra]
        start = s[:index]
        end = e[:index]
        
        return sorted(tid for tid, timestamp in self.logs
                      if start <= timestamp[:index] <= end)

Because the number of operations is very small, we do not need a complicated structure to store the 
logs: a simple list will do.

=======================================================================================================
method 2:

Stats:

	- 
	- 


Method:

	-	










~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	












~~~~~~~~~~~~~~~~~~~~~~~     python      ~~~~~~~~~~~~~~~~~~~~~~~~

