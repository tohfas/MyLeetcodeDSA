/*
Microsoft | Hard | Array / Two Pointers | Trapping Rain Water
---------------------------------------------------------
Problem:
--------
Given n non-negative integers representing an elevation map where
the width of each bar is 1, compute how much water it can trap.

Pattern:
--------
- Prefix/Suffix max arrays (Dynamic Programming)
- Two-pointer optimization

Difficulty:
-----------
Hard
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   - For each bar at index i:
       * Water trapped depends on the minimum of:
         - Maximum height to the LEFT of i.
         - Maximum height to the RIGHT of i.
       * Trapped water = min(leftMax, rightMax) - height[i]
         (only if result > 0, else 0).
   - Add this for all indices.

   Step-by-step:
   -------------
   1. Iterate through each index i.
   2. Compute leftMax by scanning [0..i].
   3. Compute rightMax by scanning [i..n-1].
   4. Water at i = min(leftMax, rightMax) - height[i].
   5. Sum up all.

   Complexity Building:
   --------------------
   - For each index i (O(n)),
     we scan left (O(n)) and right (O(n)).
   - Total = O(n^2).
   - Space = O(1) (just counters).

   =========================================================
   BRUTE FORCE CODE (COMMENTED)
   =========================================================
*/
/*
class SolutionBruteForce {
    public int trap(int[] height) {
        int n = height.length;
        int totalWater = 0;

        // Loop over each bar
        for (int i = 0; i < n; i++) {
            // Find the tallest bar to the left
            int leftMax = 0;
            for (int l = 0; l <= i; l++) {
                leftMax = Math.max(leftMax, height[l]);
            }

            // Find the tallest bar to the right
            int rightMax = 0;
            for (int r = i; r < n; r++) {
                rightMax = Math.max(rightMax, height[r]);
            }

            // Water trapped at index i
            int trapped = Math.min(leftMax, rightMax) - height[i];
            if (trapped > 0) {
                totalWater += trapped;
            }
        }

        return totalWater;
    }
}

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (Brute Force):
--------------------------------------
Time = O(n^2) 
   - For each index, scanning left & right takes O(n).
Space = O(1)
   - Only integers used.

Why inefficient?
----------------
- Double scanning repeats a lot of work.
- We can precompute leftMax and rightMax once → DP arrays.
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH (Dynamic Programming with Prefix & Suffix)
   =========================================================
   Logic:
   ------
   - Precompute arrays:
     * leftMax[i] = tallest bar from [0..i].
     * rightMax[i] = tallest bar from [i..n-1].
   - Then for each i:
     trappedWater[i] = min(leftMax[i], rightMax[i]) - height[i].
   - Sum them up.

   Steps:
   ------
   1. Build leftMax in one forward pass.
   2. Build rightMax in one backward pass.
   3. One final loop to calculate trapped water.

   Complexity:
   -----------
   Time = O(n) (3 passes).
   Space = O(n) (for arrays).
   =========================================================
*/

class SolutionDP {
    public int trap(int[] height) {
        int n = height.length;
        if (n == 0) return 0;

        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // Build leftMax
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // Build rightMax
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        // Calculate trapped water
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            totalWater += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return totalWater;
    }
}

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (DP Solution):
--------------------------------------
Time = O(n) 
   - Forward pass + backward pass + final pass.
Space = O(n) 
   - Extra arrays leftMax and rightMax.

Better than brute force, but can we optimize further?
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH 2 (Two Pointers)
   =========================================================
   Logic:
   ------
   - Instead of extra arrays, maintain two pointers:
     * left = 0, right = n-1
     * leftMax, rightMax variables
   - At each step:
     - Compare height[left] and height[right].
     - Move the smaller side inward, since water trapped
       depends on the shorter boundary.
     - Update leftMax/rightMax and trapped water accordingly.

   Why it works:
   -------------
   - The trapped water depends on min(leftMax, rightMax).
   - By always moving the smaller side, we guarantee correctness.

   =========================================================
   OPTIMIZED CODE (COMMENTED)
   =========================================================
*/

class Solution {
    public int trap(int[] height) {
        int n = height.length;
        int left = 0, right = n - 1;  // two pointers
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // Case 1: left side is smaller
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // update leftMax
                } else {
                    totalWater += leftMax - height[left]; // trap water
                }
                left++;
            } else {
                // Case 2: right side is smaller
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // update rightMax
                } else {
                    totalWater += rightMax - height[right]; // trap water
                }
                right--;
            }
        }

        return totalWater;
    }
}

/*
=========================================================
TIME & SPACE COMPLEXITY (Two Pointer Solution)
=========================================================
Time = O(n) 
   - Single pass with two pointers.

Space = O(1)
   - Only variables for pointers and max values.

=========================================================
INTERVIEW THINKING FLOW
=========================================================
1. Brute force: For each bar, scan left and right → O(n^2).
2. Optimization: Precompute leftMax & rightMax → O(n) time, O(n) space.
3. Further optimize: Use two pointers to remove arrays → O(n) time, O(1) space.
4. Data Structure Tradeoffs:
   - Brute force: no extra space, but too slow.
   - DP arrays: faster but uses memory.
   - Two pointers: best balance of speed & memory.

Final Choice:
-------------
Two-pointer approach: O(n) time, O(1) space → most optimal.
=========================================================
*/
