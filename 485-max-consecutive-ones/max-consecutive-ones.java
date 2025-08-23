/*
Microsoft | Array | Sliding Window / Greedy | Easy

Problem:
--------
Given a binary array nums, return the maximum number of consecutive 1's in the array.

Examples:
---------
Input: [1,1,0,1,1,1] → Output: 3
    The last three 1s form the longest streak.

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
- A "streak of consecutive 1's" means we must check all subarrays and count how many 1s appear consecutively.
- Brute force method:
   1. Start from each index i.
   2. Expand forward until encountering a 0 or reaching end.
   3. Track maximum streak length.
- This repeats work, but it's the naive first approach.

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
                break; // stop when a 0 is found
            }
        }
    }
    return maxCount;
}

Step-by-step Complexity Analysis:
---------------------------------
1. Outer loop runs n times.
2. Inner loop in worst case runs n times (when array is all 1s).
3. Total operations ≈ n * n = O(n^2).
   - Example: nums=[1,1,1,1], i=0 checks 4 steps, i=1 checks 3, i=2 checks 2, i=3 checks 1 → sum ≈ n(n+1)/2.
   - So O(n^2).
4. Space: O(1) because only counters are used.

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force re-checks many segments unnecessarily.
   - Example: When array is [1,1,1,1], inner loop re-counts streaks.
2. Observation:
   - We only need to keep track of "current consecutive 1s".
   - Reset count whenever a 0 appears.
   - Update maxCount whenever count grows.
3. This becomes a simple one-pass linear scan.
4. Why best:
   - Only one traversal → O(n).
   - Constant extra variables → O(1).
5. Tradeoffs:
   - Sliding window approach is equivalent here but overkill.
   - Dynamic programming also unnecessary since we only need local streak count.
   - Greedy one-pass is simplest and most optimal.


--------------------------------------
OPTIMIZED SOLUTION (One-Pass Greedy)
--------------------------------------
*/

class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int maxCount = 0;
        int currentCount = 0;

        for (int num : nums) {
            if (num == 1) {
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);
            } else {
                currentCount = 0; // reset streak
            }
        }

        return maxCount;
    }
}

/*
Time Complexity:
---------------
- Single pass through nums → O(n).
- Each operation inside loop is O(1).
- Total = O(n).

Space Complexity:
----------------
- Only two variables → O(1).
*/
