/*
Microsoft | Array + Binary Search + Greedy Partitioning | Hard

Problem:
--------
Given an integer array nums and an integer k, split nums into k non-empty subarrays 
such that the largest sum among these subarrays is minimized.
Return that minimized largest sum.

Examples:
---------
Input: nums=[7,2,5,10,8], k=2 → Output: 18
   Best split: [7,2,5] and [10,8]. Largest sum = 18.

Input: nums=[1,2,3,4,5], k=2 → Output: 9
   Best split: [1,2,3] and [4,5]. Largest sum = 9.

Constraints:
------------
- 1 <= nums.length <= 1000
- 0 <= nums[i] <= 10^6
- 1 <= k <= min(50, nums.length)
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- We want to split nums into k subarrays.
- For each possible partitioning, calculate the largest subarray sum.
- Return the minimum across all partitions.
- This requires trying all ways to split → exponential.

Brute Force Idea:
-----------------
- Use recursion/DFS:
   1. At each index, choose whether to cut or continue subarray.
   2. Stop when we have exactly k subarrays.
   3. Track max sum of current split.
   4. Return global minimum.

Brute Force Code (Conceptual):
-----------------
public int splitArrayBruteForce(int[] nums, int k) {
    return dfs(nums, 0, k);
}

private int dfs(int[] nums, int start, int k) {
    if (k == 1) {
        // only one subarray left: take sum of remaining
        int sum = 0;
        for (int i = start; i < nums.length; i++) sum += nums[i];
        return sum;
    }

    int sum = 0, result = Integer.MAX_VALUE;
    for (int i = start; i <= nums.length - k; i++) {
        sum += nums[i];
        result = Math.min(result, Math.max(sum, dfs(nums, i + 1, k - 1)));
    }
    return result;
}

Step-by-Step Complexity:
------------------------
1. Each element can either be the end of a subarray or not.
2. Branching = O(2^n) roughly, multiplied by combinations of splits into k.
3. For n=1000 → impossible.

Final Complexity:
-----------------
- Time = Exponential (O(2^n))
- Space = O(n) recursion depth
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation 1:
--------------
- Answer lies between:
   - Lower bound = max(nums)  (a subarray must contain the largest element).
   - Upper bound = sum(nums) (all elements in one subarray).

Observation 2:
--------------
- If we "guess" a maximum allowed subarray sum (mid), 
  we can greedily check if it's possible to split nums into ≤ k subarrays.

Greedy Check:
-------------
- Traverse nums, accumulate sum.
- If sum > mid → start new subarray.
- Count subarrays formed.
- If count > k → mid too small.
- Else → mid works.

Binary Search:
--------------
- Binary search between [max(nums), sum(nums)].
- Check feasibility with greedy function.
- Narrow until we find minimal possible largest sum.

Why This is Best:
-----------------
- Brute force tries all partitions (exponential).
- DP possible, but O(n^2*k) (slow for n=1000).
- Binary Search + Greedy = O(n log(sum)) → feasible.
- Tradeoffs:
   - Brute force impossible.
   - DP slower for large n.
   - Binary search optimal here.


--------------------------------------
OPTIMIZED SOLUTION (Binary Search + Greedy Check)
--------------------------------------
*/

class Solution {
    public int splitArray(int[] nums, int k) {
        int maxNum = 0, totalSum = 0;

        // find bounds
        for (int num : nums) {
            maxNum = Math.max(maxNum, num); // lower bound
            totalSum += num;                // upper bound
        }

        int left = maxNum, right = totalSum, result = totalSum;

        while (left <= right) {
            int mid = left + (right - left) / 2; // candidate max sum

            if (canSplit(nums, k, mid)) {
                result = mid;          // feasible, try smaller
                right = mid - 1;
            } else {
                left = mid + 1;        // not feasible, need bigger
            }
        }

        return result;
    }

    // Greedy check: can we split nums into <= k subarrays where each sum ≤ maxSum?
    private boolean canSplit(int[] nums, int k, int maxSum) {
        int subarrayCount = 1, currSum = 0;

        for (int num : nums) {
            if (currSum + num > maxSum) {
                subarrayCount++;    // start new subarray
                currSum = num;
                if (subarrayCount > k) return false; // too many subarrays
            } else {
                currSum += num;
            }
        }
        return true;
    }
}

/*
Time Complexity:
---------------
- Binary search range = sum(nums) - max(nums).
- Each feasibility check = O(n).
- Total = O(n log(sum(nums))).

Space Complexity:
----------------
- O(1), only variables used.

--------------------------------------
Interview Tip:
--------------
- Always start with brute force → exponential.
- Show why bounds = [max(nums), sum(nums)].
- Explain greedy check function.
- Conclude with O(n log(sum)) solution.
*/
