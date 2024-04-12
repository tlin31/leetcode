Google - compare strings

https://leetcode.com/discuss/interview-question/352458/


=======================================================================================================
method 1:

method:

	- 
	- 

stats:

	- 
	- 
 
public static int[] compare(String A, String B) {	 
		 String[] a = A.split(",");
		 String[] b = B.split(",");
		 int[] res = new int[b.length];
		 int[] helper = new int[a.length];
		 int[] helper2 = new int[b.length];
		 Map<Character,Integer> map = new HashMap<Character,Integer>();
		 Map<Character,Integer> charMap = new HashMap<Character,Integer>();

		 for(int i=0;i<a.length;++i) {
			 char[] temp = a[i].toCharArray();
			 Arrays.parallelSort(temp);
			 charMap.clear();
			 for(int j=0;j<temp.length;++j) {
				 if(charMap.getOrDefault(temp[j],0)==0){
					 charMap.put(temp[j],1);
				 }
				 else {
					 charMap.put(temp[j],charMap.get(temp[j])+1);
				 }
			 }
			 a[i] = new String(temp);
			 helper[i] = charMap.get(temp[0]);
		 }
		 for(int i=0;i<b.length;++i) {
			// map.put(i,new HashMap<Character,Integer>());
			 char[] temp = b[i].toCharArray();
			 Arrays.parallelSort(temp);
			 charMap.clear();
			 for(int j=0;j<temp.length;++j) {
				 if(map.getOrDefault(temp[j], 0)==0) {
					 map.put(temp[j], 1);
				 }
				 else {
					 map.put(temp[j],map.get(temp[j])+1);
				 }
			 }
			 b[i] = new String(temp);
			 helper2[i] = map.get(temp[0]);
		 }
		 
		 for(int i=0;i<res.length;++i) {
			 int counter=0;
			 for(int j=0;j<a.length;++j) {
				 if(helper2[i] > helper[j]) {
					 counter++;
				 }
			 }
			 res[i] = counter;
		 }
		 
		 return res;
	 }
=======================================================================================================
method 2:

method:

	- 
	- 

stats:

	- 
	- 

// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        String[] arr1 = new String[]{"aabc","bd","abcd"};
        String[] arr2 = new String[]{"aaa","aa"};

        // create new comparator
        Comparator<String> comp = new Comparator<String>(){
            public int compare(String str1,String str2){
                char[] charArr1 = str1.toCharArray();
                char[] charArr2 = str2.toCharArray();
                Arrays.sort(charArr1);
                Arrays.sort(charArr2);
                int count1=1,
                    count2=1;
                int ptr1=0,
                    ptr2=0;
                while(ptr1<str1.length()-1 && str1.charAt(ptr1)==str1.charAt(ptr1+1)) {
                	count1++; 
                	ptr1++;
            	}

                while(ptr2<str2.length()-1 && str2.charAt(ptr2)==str2.charAt(ptr2+1)) {
                	count2++; 
                	ptr2++;
                }
                if(count2 <= count1) return 1;
                else return -1;
            }
        };
        Arrays.sort(arr1,comp);

        
        int c=0;
        int[] res = new int[arr2.length];
        for(String s:arr2){
            int index = Arrays.binarySearch(arr1,s,comp);
            int count = -(i+1);
            System.out.println(i);
            res[c++] = count;
        }
        for(int i:res) 
        	System.out.println(i);
    }
}


===============================================================================================
class CompareStrings {
    public int[] compare(String A, String B) {
    	String[] a = A.split(",");
		String[] b = B.split(",");

        int res[] = new int[b.length];
        int c = 0;

        int count1[] = new int[a.length];
        int count2[] = new int[b.length];

        for (int i = 0; i < a.length; i++) {
            char arr1[] = a[i].toCharArray();
            count1[i] = count(arr1);
        }
        for (int i = 0; i < b.length; i++) {
            char arr1[] = b[i].toCharArray();
            count2[i] = count(arr1);
        }
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < a.length; j++) {
                if (count2[i] > count1[j])
                    res[c]++;
            }
            c++;
        }
        return res;
    }

    public int count(char ar[]) {
        Arrays.sort(ar);
        int count = 1;
        for (int i = 1; i < ar.length; i++) {
            if (ar[i] != ar[i - 1])
                break;
            else
                count++;

        }

        return count;
    }
}



