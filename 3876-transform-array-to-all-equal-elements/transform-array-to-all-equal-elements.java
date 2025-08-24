/*
Microsoft | Medium | Array | Greedy Pattern
------------------------------------------------
Problem:
--------
You are given an array nums[] of size n containing only 1 and -1,
and an integer k.

Operation allowed (at most k times):
- Pick index i (0 ≤ i < n-1) and multiply both nums[i] and nums[i+1] by -1.

Goal:
-----
Return true if it is possible to make all elements of nums equal
(after ≤ k operations), else false.

Difficulty:
-----------
Medium
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   - Try every possible sequence of ≤ k operations.
   - After each sequence, check if array becomes uniform (all 1 or all -1).
   - Backtracking/DFS can be used.

   Step-by-step:
   -------------
   1. Define dfs(nums, k).
   2. If all nums equal → return true.
   3. If k == 0 → return false.
   4. Try each index i from 0..n-2:
        - Flip nums[i], nums[i+1].
        - Recurse with k-1.
        - Undo flip.
   5. If any recursion returns true → return true.

   Why is it exponential?
   ----------------------
   - At each step, ~n choices.
   - Depth ≤ k.
   - Worst case = O(n^k).
   - Completely infeasible for n up to 1e5.

   Still useful for interview to build intuition.

   =========================================================
   BRUTE FORCE CODE (Exponential Backtracking)
   =========================================================
*/
/*
class SolutionBruteForce {
    public boolean canMakeEqual(int[] nums, int k) {
        return dfs(nums, k);
    }

    private boolean dfs(int[] nums, int k) {
        // Check if all equal
        boolean allEqual = true;
        for (int num : nums) {
            if (num != nums[0]) {
                allEqual = false;
                break;
            }
        }
        if (allEqual) return true;
        if (k == 0) return false;

        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            // flip nums[i] and nums[i+1]
            nums[i] *= -1;
            nums[i + 1] *= -1;

            if (dfs(nums, k - 1)) {
                return true;
            }

            // backtrack
            nums[i] *= -1;
            nums[i + 1] *= -1;
        }
        return false;
    }
}
*/

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (Brute Force)
------------------------------------------------------------
Time = O(n^k) → exponential, infeasible for n=1e5.
Space = O(n) for recursion stack.
------------------------------------------------------------
Why not good?
-------------
Because n and k are large, brute force is impossible.
But it helps us understand: the problem is about
eliminating "transitions" between 1 and -1.
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH (Greedy)
   =========================================================
   Key Insight:
   ------------
   - The only problem is "boundaries" where nums[i] != nums[i+1].
   - Each operation can eliminate one such mismatch by flipping both sides.
   - We simulate greedily from left to right.

   Core Idea:
   ----------
   - Try to make all = 1.
   - Try to make all = -1.
   - In each attempt:
       * Scan left → right.
       * If arr[i] != target, flip (i, i+1).
       * Count flips.
       * At the end, check if last element == target and flips ≤ k.

   Dry Run Example:
   ----------------
   nums = [-1,-1,-1,1,1,1], k=5
   - Target = 1 → fails, needs more flips.
   - Target = -1 → also fails.
   So answer = false.

   =========================================================
   OPTIMIZED CODE
   =========================================================
*/

class Solution {
    public boolean canMakeEqual(int[] nums, int k) {
        return canConvert(nums, k, 1) || canConvert(nums, k, -1);
    }

    // Greedy check: try to convert to target (either 1 or -1)
    private boolean canConvert(int[] nums, int k, int target) {
        int n = nums.length;
        int[] arr = nums.clone(); // copy to simulate
        int ops = 0;

        for (int i = 0; i < n - 1; i++) {
            if (arr[i] != target) {
                // flip arr[i] and arr[i+1]
                arr[i] *= -1;
                arr[i + 1] *= -1;
                ops++;
            }
        }

        // After all flips, check if last matches target
        return arr[n - 1] == target && ops <= k;
    }
}

/*
------------------------------------------------
TIME & SPACE COMPLEXITY
------------------------------------------------
Time = O(2 * n) = O(n)   (two passes: target=1, target=-1)
Space = O(n)             (cloned array for simulation)

------------------------------------------------
INTERVIEW THINKING FLOW
------------------------------------------------
1. Brute force: try all flips → O(n^k), infeasible.
2. First greedy attempt: just count transitions → fails.
3. Correct greedy: simulate left-to-right with target.
   - Either all = 1 OR all = -1.
   - Minimal guaranteed operations.
   - Compare with k.

Tradeoffs:
----------
- Brute force is correct but exponential.
- Transition counting is too naive.
- Left-to-right greedy works in O(n).
------------------------------------------------
*/
