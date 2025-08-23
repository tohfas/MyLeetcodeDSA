/*
Microsoft | Array | Sliding Window / Prefix Sum + Binary Search | Medium

Problem:
--------
Given an array of positive integers nums and a positive integer target,
return the minimal length of a subarray whose sum is >= target.
If no such subarray exists, return 0.

Examples:
---------
Input: target=7, nums=[2,3,1,2,4,3] → Output: 2
    Subarray [4,3] has length 2 and sum = 7.

Input: target=4, nums=[1,4,4] → Output: 1
    Subarray [4] has length 1.

Input: target=11, nums=[1,1,1,1,1,1,1,1] → Output: 0
    No subarray has sum ≥ 11.

Constraints:
------------
- 1 <= target <= 10^9
- 1 <= nums.length <= 10^5
- 1 <= nums[i] <= 10^4
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- Try all possible subarrays [i..j].
- For each start index i:
   - Expand to j = i..n-1, calculate sum.
   - If sum >= target, record length and break (shortest for this i).
- Track global minimum length.

Brute Force Code:
-----------------
public int minSubArrayLen(int target, int[] nums) {
    int n = nums.length;
    int minLen = Integer.MAX_VALUE;

    for (int i = 0; i < n; i++) {
        int sum = 0;
        for (int j = i; j < n; j++) {
            sum += nums[j];
            if (sum >= target) {
                minLen = Math.min(minLen, j - i + 1);
                break; // no need to expand further
            }
        }
    }

    return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
}

Step-by-Step Complexity:
------------------------
1. Outer loop runs n times.
2. Inner loop worst case runs ~n/2 on average.
3. Total ~ O(n^2) in worst case.
   Example: nums = [1,1,1,...] and target large → every subarray explored.
4. Space = O(1) (only counters).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force wastes time recalculating sums.
2. Observation:
   - All nums[i] are positive.
   - This allows **sliding window**:
     * Expand window by moving right pointer.
     * If sum >= target → shrink window by moving left pointer.
     * Track minimal length.
3. Why Sliding Window is Best:
   - Each element added once and removed once.
   - Time = O(n), Space = O(1).
4. Tradeoffs:
   - Prefix sum + binary search gives O(n log n).
   - Sliding window is strictly better → O(n).
   - But in interviews, mention both to show awareness.


--------------------------------------
OPTIMIZED SOLUTION (Sliding Window O(n))
--------------------------------------
*/

class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int left = 0, sum = 0, minLen = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += nums[right]; // expand window

            // shrink window while valid
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
    }
}

/*
Time Complexity (Sliding Window):
---------------------------------
- Each element added once, removed once → O(n).
- Total = O(n).

Space Complexity (Sliding Window):
----------------------------------
- Only variables → O(1).
*/


/*
--------------------------------------
FOLLOW-UP (O(n log n) using Prefix Sum + Binary Search)
--------------------------------------
- Build prefixSum array: prefix[i] = sum of nums[0..i-1].
- For each i, need smallest j where prefix[j] - prefix[i] >= target.
- Use binary search to find j.
- Complexity = O(n log n).
*/
