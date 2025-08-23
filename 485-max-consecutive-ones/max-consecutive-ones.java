/*
Microsoft | Array | Greedy / Sliding Window | Easy

Problem:
--------
Given a binary array nums, return the maximum number of consecutive 1's in the array.

Examples:
---------
Input: [1,1,0,1,1,1] → Output: 3
    Longest streak of 1s = last three elements.

Input: [1,0,1,1,0,1] → Output: 2

Constraints:
------------
- 1 <= nums.length <= 10^5
- nums[i] ∈ {0,1}
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- A "streak of consecutive 1s" means we must check subarrays.
- Brute approach:
   1. Start from each index i.
   2. Expand forward until we hit a 0 or reach end.
   3. Track maximum streak length.
- This re-checks many elements → quadratic.

Brute Force Code:
-----------------
public int findMaxConsecutiveOnes(int[] nums) {
    int maxCount = 0;
    for (int i = 0; i < nums.length; i++) {
        int count = 0;
        for (int j = i; j < nums.length; j++) {
            if (nums[j] == 1) {
                count++;
                maxCount = Math.max(maxCount, count);
            } else {
                break; // stop when 0 encountered
            }
        }
    }
    return maxCount;
}

Step-by-Step Complexity Reasoning:
----------------------------------
1. Outer loop runs n times (index i).
2. Inner loop in worst case runs n times (array full of 1s).
3. Total ≈ n + (n-1) + (n-2) + ... + 1 = n(n+1)/2.
4. Asymptotic complexity → O(n^2).

Space Complexity:
-----------------
- Only counters are used.
- No extra data structures.
- Space = O(1).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force wastes time by re-checking elements.
   - Example: [1,1,1,1], inner loop counts overlapping streaks.
2. We only need to maintain a **current streak of 1s**.
   - Reset streak when we encounter 0.
   - Track maximum streak.
3. This reduces to a **single pass** → O(n).
4. Why Greedy is Best:
   - We don’t need sliding window or DP (overkill).
   - Just counters are enough.
5. Tradeoffs:
   - Brute force O(n^2) → impossible for n=10^5.
   - Greedy scan O(n), O(1) → optimal.


--------------------------------------
OPTIMIZED SOLUTION (Greedy Clean Version)
--------------------------------------
*/
/*
class Solution1 {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0;
        int currentCount = 0;

        for (int num : nums) {
            if (num == 1) {
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);
            } else {
                currentCount = 0;
            }
        }
        return maxCount;
    }
}
*/
/*
Time Complexity:
---------------
- Single pass → O(n).

Space Complexity:
----------------
- Only two counters → O(1).
*/


/*
--------------------------------------
MICRO-OPTIMIZED VERSION (Avoid Math.max + Index Loop)
--------------------------------------
- Avoids Math.max call inside loop (branchless improvement).
- Uses index-based loop instead of enhanced-for (avoids iterator).
- On large inputs, runs slightly faster.
*/

class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0, currentCount = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                currentCount++;
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                }
            } else {
                currentCount = 0;
            }
        }

        return maxCount;
    }
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/


/*
--------------------------------------
COMPARISON TABLE
--------------------------------------

| Approach             | Time   | Space | Notes                          |
|----------------------|--------|-------|--------------------------------|
| Brute Force          | O(n^2) | O(1)  | Re-checks subarrays repeatedly |
| Greedy Clean Version | O(n)   | O(1)  | Simple, readable, optimal      |
| Micro-Optimized      | O(n)   | O(1)  | Faster in practice, avoids lib |

--------------------------------------

Interview Tip:
--------------
- Always start with brute force → explain why it's O(n^2).
- Show insight: just track current streak → O(n).
- Implement Greedy clean version.
- Optionally mention micro-optimizations (avoid Math.max overhead).
*/
