/*
Microsoft | Dynamic Programming + Monotonic Stack | Hard
------------------------------------------------------------
Problem:
--------
Given a binary matrix (rows x cols), find the largest rectangle
containing only 1's and return its area.

Pattern:
--------
- "Largest Rectangle in Histogram" extended to 2D.
- For each row, treat it as a histogram and compute max area.

Difficulty:
-----------
Hard
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   1. For every cell (i, j) that is '1':
      - Expand rectangle downwards and rightwards.
      - At each step, compute area of rectangle filled with 1s.
   2. Track the maximum area found.

   Steps:
   ------
   - Iterate all possible top-left corners (O(rows * cols)).
   - For each top-left, iterate all bottom-right corners (O(rows * cols)).
   - For each rectangle, check if all cells are 1 (O(rows * cols)).

   Total Time Complexity:
   ----------------------
   O((rows * cols)^3). Way too large for 200x200 → TLE.

   Space Complexity:
   -----------------
   O(1), only area tracking.

------------------------------------------------------------
   BRUTE FORCE CODE (for interview explanation only):
------------------------------------------------------------
*/
/*
class SolutionBruteForce {
    public int maximalRectangle(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int maxArea = 0;

        // Try all top-left corners
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    // Try all bottom-right corners
                    for (int x = i; x < rows; x++) {
                        for (int y = j; y < cols; y++) {
                            boolean allOnes = true;
                            // Verify rectangle i..x, j..y is all '1's
                            for (int p = i; p <= x; p++) {
                                for (int q = j; q <= y; q++) {
                                    if (matrix[p][q] == '0') {
                                        allOnes = false;
                                        break;
                                    }
                                }
                                if (!allOnes) break;
                            }
                            if (allOnes) {
                                int area = (x - i + 1) * (y - j + 1);
                                maxArea = Math.max(maxArea, area);
                            }
                        }
                    }
                }
            }
        }
        return maxArea;
    }
}
*/

/*
------------------------------------------------------------
Step-by-step Complexity (Brute Force):
- Choosing top-left corner: O(rows * cols).
- Choosing bottom-right corner: O(rows * cols).
- Validating rectangle: O(rows * cols).
- Total = O((rows * cols)^3).
- Space = O(1).
------------------------------------------------------------
Limitations:
- Correct but infeasible for large inputs (200x200).
- Gives intuition for rectangle checking.
========================================================
*/


/* =========================================================
   OPTIMIZED APPROACH (Histogram + Largest Rectangle in Histogram)
   =========================================================
   Logic:
   ------
   1. Treat each row as the "base" of a histogram.
   2. Build `heights[j]` = number of consecutive 1's above (including current row).
   3. For each row, compute "Largest Rectangle in Histogram" using stack.
   4. Update max area.

   Example:
   --------
   matrix:
   1 0 1 0 0
   1 0 1 1 1
   1 1 1 1 1
   1 0 0 1 0

   Heights row by row:
   Row1 → [1,0,1,0,0]
   Row2 → [2,0,2,1,1]
   Row3 → [3,1,3,2,2]
   Row4 → [4,0,0,3,0]

   For each heights[], compute largest rectangle.

   Why Stack?
   ----------
   - Stack maintains increasing bars.
   - Efficiently compute max area in O(cols).

   Tradeoffs:
   ----------
   - Brute force is intuitive but too slow.
   - Histogram + stack is harder but optimal.

========================================================
*/

import java.util.*;

class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols]; // histogram heights
        int maxArea = 0;

        // Process each row as histogram base
        for (int i = 0; i < rows; i++) {
            // Update histogram heights
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] += 1; // increase height if '1'
                } else {
                    heights[j] = 0;  // reset if '0'
                }
            }
            // Compute largest rectangle for this histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }

        return maxArea;
    }

    // Helper: Largest Rectangle in Histogram using stack
    private int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i]; // sentinel 0 at end
            // Maintain increasing stack
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int right = i;
                int left = stack.isEmpty() ? -1 : stack.peek();
                int width = right - left - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }

        return maxArea;
    }
}

/*
=========================================================
TIME & SPACE COMPLEXITY
=========================================================
Brute Force:
------------
- Time: O((rows * cols)^3).
- Space: O(1).
- Not feasible for rows, cols = 200.

Optimized Histogram + Stack:
----------------------------
- Updating heights: O(rows * cols).
- Largest Rectangle in Histogram: O(cols) per row.
- Total Time: O(rows * cols).
- Space: O(cols) for heights[] + O(cols) for stack.

=========================================================
INTERVIEW THINKING FLOW
=========================================================
1. Brute force: Try all rectangles → too slow, O(n^6) for 2D.
2. Realization: Rectangles are made of consecutive '1's → histograms.
3. Optimize by reducing 2D rectangle problem → 1D histogram problem.
4. Choose stack (monotonic increasing) because:
   - Efficiently computes largest rectangle in O(n).
   - Avoids re-checking all subrectangles.
5. Tradeoff: More complex logic, but scales to 200x200.

=========================================================
*/
