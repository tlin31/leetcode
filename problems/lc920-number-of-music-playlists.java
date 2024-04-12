920. Number of Music Playlists - Hard

Your music player contains N different songs and she wants to listen to L (not necessarily different) 
songs during your trip.  You create a playlist so that:

	1. Every song is played at least once
	2. A song can only be played again only if K other songs have been played

Return the number of possible playlists.  

As the answer can be very large, return it modulo 10^9 + 7.

 

Example 1:

Input: N = 3, L = 3, K = 1
Output: 6
Explanation: There are 6 possible playlists. [1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1].


Example 2:

Input: N = 2, L = 3, K = 0
Output: 6
Explanation: There are 6 possible playlists. [1, 1, 2], [1, 2, 1], [2, 1, 1], [2, 2, 1], [2, 1, 2], [1, 2, 2]
Example 3:

Input: N = 2, L = 3, K = 1
Output: 2
Explanation: There are 2 possible playlists. [1, 2, 1], [2, 1, 2]
 

Note:

0 <= K < N <= L <= 100


******************************************************
key:
	- DP, think seperately as add a new song or old song
	- edge case:
		1) empty string, return empty
		2)

******************************************************

=======================================================================================================
method 1:

Stats:

	- 
	- 


Method:

	Simplified --> without constraint of K:
	-  dp[i][j] = solution of i songs with j different songs. So the final answer should be dp[L][N]


	- Base case:
		dp[0][0] = 1		// fill an empty list with 0 songs, there's only 1 way to do so.

		dp[0][j] = 0		// actually for all i > j are invalid cases, because can not put more 
							// songs to a smaller list
							// The same rule applies to i > 0 cases, e.g. dp[1][j] are also 0 for j > 1.

		dp[i][0] = 0		// can not fill a non-empty list with 0 songs, the j = 0 case is always 0.

	- Think one step before the last one:
		case 1 (the last added one is new song): 
			listen i - 1 songs with j - 1 different songs, then the last one is definitely new song 
			with the choices of N - (j - 1).

		Case 2 (the last added one is old song): 
			listen i - 1 songs with j different songs, then the last one is definitely old song with 
			the choices of j

		dp[i][j] = dp[i-1][j-1] * (N - (j-1)) + dp[i-1][j] * j



	If with the constaint of K:
		Case 1 - if the last added one is new song
			(= simplified case 1) listen i - 1 songs with j - 1 different songs, then the last one 
			is definitely new song with the choices of N - (j - 1).
			
				dp[i-1][j-1] * (N - (j-1))
		
		Case 2 - last added is a old song, (j = # of unique songs)
			if j > k
				= dp[i-1][j] * (j-k)
				It should be updated j - k because k songs can't be chosed from j - 1 to j - k. 
			
			if j <= K, 
				= 0 
				because can't choose old song if the current songs are still within K, we only
				    chooes old song after choosing K different other songs

	Thus:
		if (j > k)
			dp[i][j] = dp[i-1][j-1] * (N- (j-1)) + dp[i-1][j] * (j-k)
		else
			dp[i][j] = dp[i-1][j-1] * (N- (j-1))





class Solution {
    public int numMusicPlaylists(int N, int L, int K) {
        int mod = (int)Math.pow(10, 9) + 7;
        long[][] dp = new long[L+1][N+1];
        dp[0][0] = 1;
        for (int i = 1; i <= L; i++){
            for (int j = 1; j <= N; j++){
                dp[i][j] = (dp[i-1][j-1] * (N - (j-1)))%mod; 

                if (j > K){
                    dp[i][j] += (dp[i-1][j] * (j-K)) %mod) %mod;
                }
            }
        }
        return (int)dp[L][N];
    }
}


=======================================================================================================
Method 2:


Stats:

	- Time Complexity: O((L-K)(N-K))
	- 


Method:

	- First, simplified this problem as : Get n*r of ways to build playlist of length L out of N 
	  distinct songs. 
			F1(N, L) = 
			   1, 		if L=0  // base case, one way to build empty playlist
			   F1(N, L-1) * N  // ways to build prefix times ways to choose last song
	

	- Simplified problem 2: every song has to be played at least once. 
		F2(N, L) - nr = # ways to build playlist of length L out of N distinct songs with guarantee 
		                that each distinct song is played at least once.

		Intuition: think about 1 new song and delegate the rest to F2(N-1, ...). 
		           At the point we introduce a new song, we are bound to have valid playlist prefix 
		           that does not contain this song & the playlist already contains n-1 songs. 
		           There are (nr) # of ways to build such prefix is F2(N-1, L-1) & there are N ways to
		           chose a new song, hence F2(N-1, L-1) * N term. 

		           Note that F2(N-1, ...) does not mean we build playlist out of first N-1 songs in 
		           some ordered list 1,2,3,...,N-1. 

		           F2(N-1, ...) means we use N-1 distinct songs without specifying which ones.

We can not simply declare that F2(N, L) = F2(N-1, L-1) * N, because introduction of new song is not the only way to have playlist N,L. We can also have playlist prefix that already introduced all N songs, and we play one of those, hence F2(N, L-1) * N term.

F2(N, L)
   0, if (L=0) != (N=0)  // base case 2. One param is zero, the other is not. 
                         // No way to build empty list and use N>0 songs at least once. 
                         // No way to build L>0 list out of 0 songs.
   1, if L=0 and N=0  // base case 1, there exists one way to build empty playlist
   F2(N-1, L-1)*N  +  F2(N, L-1, K)*N


Original problem
Let's add constraint about no reusing of last K songs. F3(N, L, K) - nr of ways to build playlist of length L out of N distinct songs with guarantee that each distinct song is played at least once and individual song is used again only when K other songs played.
That's relatively easy. K limits the choice of the song to reuse in the second term F2(N, L-1, K) * N -> F2(N, L-1, K) * (N-K). When playlist is shorter than K (N-K<0), there is no way to reuse songs and second term should be zeroed.

F3(N,L,K)
   0, if (L=0) != (N=0)  // base case 2. One param is zero, the other is not. No way to build empty list and use N>0 songs at least once. No way to build L>0 list out of 0 songs.
   1, if L=0 and N=0  // base case 1, there exists one way to build empty playlist
   F2(N-1, L-1, K)*N  +  F2(N, L-1, K)*max(0, N-K)
Code
Exponential recursion

int numMusicPlaylists(const int N, const int L, const int K) {
    function<int(const int n /* nr of songs */, const int l /* playlist len */)> dp;
    dp = [K, &dp](const int n, const int l) {
        if ((l == 0) != (n == 0)) {
            return 0;
        } else if (l == 0 && n == 0) {
            return 1;
        } else {
            return
                dp(n-1, l-1) * n + 
                dp(n, l-1) * max(0, n-K);
        }
    };
    return dp(N, L);
}



----------------------
F(N,L,K) = F(N - 1, L - 1, K) * N + F(N, L - 1, K) * (N - K)

F(N - 1, L - 1, K)
If only N - 1 in the L - 1 first songs.
We need to put the rest one at the end of music list.
Any song can be this last song, so there are N possible combinations.

F(N, L - 1, K)
If already N in the L - 1 first songs.
We can put any song at the end of music list,
but it should be different from K last song.
We have N - K choices.


	-	


    long mod = (long)1e9 + 7;
    public int numMusicPlaylists(int N, int L, int K) {
        long[][] dp = new long[N + 1][L + 1];
        for (int i = K + 1; i <= N; ++i)
            for (int j = i; j <= L; ++j)
                if ((i == j) || (i == K + 1))
                    dp[i][j] = factorial(i);
                else
                    dp[i][j] = (dp[i - 1][j - 1] * i + dp[i][j - 1] * (i - K))  % mod;
        return (int) dp[N][L];
    }

    long factorial(int n) {
        return n > 0 ? factorial(n - 1) * n % mod : 1;
    }


=======================================================================================================
method 3:

Stats:

	- 
	- 


Method:

	-	
	-	



