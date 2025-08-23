/*
Microsoft | Matrix | In-place Transformation | Medium

Problem:
--------
You are given an n x n 2D matrix representing an image.
Rotate the image by 90 degrees (clockwise).

- Must rotate in-place (cannot allocate another 2D matrix).
- Modify the input matrix directly.

Examples:
---------
Input: [[1,2,3],[4,5,6],[7,8,9]]
Output:[[7,4,1],[8,5,2],[9,6,3]]

Input: [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
Output:[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

Constraints:
------------
- n == matrix.length == matrix[i].length
- 1 <= n <= 20
- -1000 <= matrix[i][j] <= 1000
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

How to Think Brute Force:
-------------------------
- If rotation was allowed into a NEW matrix:
  - Rule: element at (i, j) → (j, n-1-i) in rotated matrix.
- Copy all values into a new matrix following this mapping.
- Replace the original matrix at the end.

Brute Force Code:
-----------------
public void rotateBruteForce(int[][] matrix) {
    int n = matrix.length;
    int[][] rotated = new int[n][n];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            rotated[j][n - 1 - i] = matrix[i][j];
        }
    }

    // Copy back to original
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matrix[i][j] = rotated[i][j];
        }
    }
}

Step-by-Step Complexity:
------------------------
1. Outer loop = n, inner loop = n → O(n^2).
2. Copy back = O(n^2).
3. Total time = O(n^2).
4. Extra space = O(n^2) for rotated matrix.

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(n^2) (not allowed, since problem demands in-place)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation:
------------
- We need in-place rotation.
- Notice rotation = "Transpose + Reverse Rows".

Optimized Idea:
---------------
1. Transpose the matrix:
   - Swap matrix[i][j] with matrix[j][i] for all i < j.
   - Converts rows → columns.
2. Reverse each row:
   - Reverses the order of elements, making it a 90° rotation.

Why This Data Structure:
------------------------
- Matrix is already square → transpose works perfectly.
- In-place swaps avoid extra space.

Tradeoffs:
----------
- Brute force uses O(n^2) space.
- Optimized uses O(1) extra space and achieves O(n^2) time.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Transpose + Reverse)
--------------------------------------
*/

class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix (mirror across diagonal)
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }
}

/*
Time Complexity:
---------------
- Transpose: O(n^2).
- Reverse each row: O(n^2).
- Total = O(n^2).

Space Complexity:
----------------
- Only temporary variables for swaps → O(1).

--------------------------------------
Interview Tip:
--------------
1. Start with brute force (extra matrix, O(n^2) space).
2. Show mapping (i, j) → (j, n-1-i).
3. Transition to in-place using Transpose + Reverse Rows.
4. Stress why square matrix makes this possible.
*/
