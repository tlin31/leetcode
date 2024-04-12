Geeks - Find safe cells in a matrix

https://www.geeksforgeeks.org/find-safe-cells-in-a-matrix/


Given a matrix mat[][] containing the characters Z, P and * where Z is a zombie, P is a plant and * is a 
bare land. Given that a zombie can attack a plant if the plant is adjacent to the zombie (total 8 adjacent
cells are possible). 

The task is to print the number of plants that are safe from the zombies.

Examples:

Input: 
mat[] = { "**P*",
          "*Z**", 
          "*Z**", 
          "***P" }
Output: 1

Input: 
mat[] = { "**P*P", 
          "*Z**", 
          "*P**", 
          "***P" }
Output: 2

******************************************************
key:
	- check plant & whether one of its neighbor is zombie
	- edge case:
		1) empty string, return empty
		2)

******************************************************



=======================================================================================================
method 1:

method:

	- Traverse the matrix element by element, if the current element is a plant i.e. mat[i][j] = ‘P’ 
	  then check if the plant is surrounded by any zombie (in all the 8 adjacent cells). 

	  If the plant is safe then update count = count + 1. Print the count in the end.


stats:

	- 
	- 


class GfG 
{ 

	// Function that returns true if mat[i][j] is a zombie 
	static boolean isZombie(int i, int j, int rowLimit, int colLimit, String mat[]) { 
		if (i < 0 || j < 0 || i >= rowLimit || j >= colLimit || mat[i].charAt(j) != 'Z') 
			return false; 
	
		return true; 
	} 
	
	// Function to return the count of plants that survived from the zombies attack 
	static int Plant_Vs_Zombies(String mat[], int row, int col) { 
		int i, j, count = 0; 
	
		for (i = 0; i < row; i++) { 
			for (j = 0; j < col; j++) { 
	
				// If current cell is a plant 
				if (mat[i].charAt(j) == 'P') { 
	
					// If current plant is safe from zombies--> check 8 neighbors
					if 	  (!isZombie(i - 1, j - 1, 	row, 	col, 	mat) 
						&& !isZombie(i - 1, j, 		row, 	col, 	mat) 
						&& !isZombie(i - 1, j + 1, 	row, 	col, 	mat) 
						&& !isZombie(i, 	j - 1, 	row, 	col, 	mat) 
						&& !isZombie(i, 	j, 		row, 	col, 	mat) 
						&& !isZombie(i, 	j + 1, 	row, 	col, 	mat) 
						&& !isZombie(i + 1, j - 1, 	row, 	col, 	mat) 
						&& !isZombie(i + 1, j, 		row, 	col, 	mat) 
						&& !isZombie(i + 1, j + 1, 	row, 	col, 	mat)) { 
						count++; 
					} 
				} 
			} 
		} 
		return count; 
	} 

	// Driver code 
	public static void main(String []args) 
	{ 
		
		// Input matrix 
		String[] mat = { "**P*", "*Z**", "*Z**", "***P" }; 
	
		// Rows and columns of the matrix 
		int row = mat.length; 
		int col = mat[0].length(); 
	
		// Total plants survived 
		System.out.println(Plant_Vs_Zombies(mat, row, col)); 
	} 
} 








