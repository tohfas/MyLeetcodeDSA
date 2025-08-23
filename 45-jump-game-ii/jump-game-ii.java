/*
Microsoft | Array | Greedy (Range Expansion) Pattern | Medium

Problem:
--------
You are given a 0-indexed array nums where each element nums[i] represents 
the maximum jump length from that index.  
You start at index 0 and must reach the last index (n-1).  
Return the minimum number of jumps needed.

Examples:
---------
Input: [2,3,1,1,4] → Output: 2
    Jump from index 0 → 1, then 1 → 4.

Input: [2,3,0,1,4] → Output: 2

Constraints:
------------
- 1 <= nums.length <= 10^4
- 0 <= nums[i] <= 1000
- It's guaranteed you can reach nums[n - 1].


--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- At every index, we can jump anywhere from 1 up to nums[i].
- This means from index 0 we try all possible next positions,
  then recursively solve the subproblem.
- Recurrence: minJumps(pos) = 1 + min(minJumps(pos + j)) for all j in [1..nums[pos]].
- Base case: if pos >= n-1, return 0 (already at or beyond last index).

Brute Force Code:
-----------------
public int jump(int[] nums) {
    return helper(nums, 0);
}

private int helper(int[] nums, int pos) {
    // Base case: reached or crossed last index
    if (pos >= nums.length - 1) return 0;

    int steps = nums[pos];
    int minJumps = Integer.MAX_VALUE;

    // Try all possible jumps
    for (int i = 1; i <= steps; i++) {
        int next = helper(nums, pos + i);
        if (next != Integer.MAX_VALUE) {
            minJumps = Math.min(minJumps, 1 + next);
        }
    }
    return minJumps;
}

Why This Time Complexity:
-------------------------
- Suppose array length = n.
- From each index, in worst case, nums[i] ~ n (can jump to every later index).
- That means branching factor ~ n, and recursion depth ~ n.
- This leads to O(n^n) in worst case (very large).
- A tighter bound: roughly O(2^n), since every index branches into multiple paths.

Why Space Complexity:
---------------------
- Recursion depth ~ n → O(n) stack space.
- No extra structures used.

Final Complexity:
-----------------
- Time: Exponential O(2^n) 
- Space: O(n)


--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

1. Problem with brute force: 
   - Explores same subproblems repeatedly (overlapping subproblems).
   - Example: minJumps(3) will be solved multiple times.

2. Step 1: Memoization
   - Store results of subproblems in dp[].
   - Reduces time from O(2^n) → O(n^2) because each index tries all possible jumps once.

3. Step 2: Dynamic Programming
   - Bottom-up DP: dp[i] = min jumps to reach i.
   - Transition: dp[i] = min(dp[j]+1) for all j that can reach i.
   - Time: O(n^2), Space: O(n).

4. Step 3: Greedy Optimization
   - We don't need to compute all dp states.
   - Instead, track the **farthest reachable index** within the current jump.
   - If we reach end of current jump range → take another jump.
   - This guarantees minimum jumps with O(n) time, O(1) space.

5. Why Greedy is Best:
   - DP O(n^2) is too slow for n = 10^4.
   - Greedy works in linear time.
   - Tradeoffs:
     * Brute force → impossible.
     * DP → too slow.
     * BFS → O(n), but uses a queue (extra space).
     * Greedy → O(n), O(1), optimal.


--------------------------------------
OPTIMIZED SOLUTION (Greedy Range Expansion)
--------------------------------------
*/

class Solution {
    public int jump(int[] nums) {
        int jumps = 0;        // count jumps
        int currentEnd = 0;   // end of current jump range
        int farthest = 0;     // farthest we can reach in current exploration

        // Iterate until the second last index (last index doesn't need a jump)
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            // If we reach the end of the range for this jump,
            // we must "take" the jump and extend the range.
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }

        return jumps;
    }
}

/*
Time Complexity (Greedy):
-------------------------
- We iterate once through nums → O(n).
- Each update (Math.max, assignment) is O(1).
- Total: O(n).

Space Complexity (Greedy):
--------------------------
- Only variables used: jumps, currentEnd, farthest.
- No recursion, no extra structures.
- Total: O(1).
*/
