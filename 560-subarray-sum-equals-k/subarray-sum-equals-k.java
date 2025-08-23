/*
Microsoft | Array + Prefix Sum + HashMap | Medium

Problem:
--------
Given an integer array nums and an integer k, 
return the total number of subarrays whose sum equals k.

Examples:
---------
Input: nums=[1,1,1], k=2 → Output: 2
   Subarrays: [1,1] (first two), [1,1] (last two)

Input: nums=[1,2,3], k=3 → Output: 2
   Subarrays: [1,2], [3]

Constraints:
------------
- 1 <= nums.length <= 2*10^4
- -1000 <= nums[i] <= 1000
- -10^7 <= k <= 10^7
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- Subarray sum(i..j) can be checked by iterating over all pairs (i, j).
- For each start index i:
   1. Expand end index j.
   2. Maintain running sum.
   3. If sum == k → increase count.

Brute Force Code:
-----------------
public int subarraySumBruteForce(int[] nums, int k) {
    int n = nums.length;
    int count = 0;

    for (int i = 0; i < n; i++) {
        int sum = 0;
        for (int j = i; j < n; j++) {
            sum += nums[j]; // calculate subarray sum
            if (sum == k) {
                count++;    // found valid subarray
            }
        }
    }

    return count;
}

Step-by-Step Complexity:
------------------------
1. Outer loop runs n times.
2. Inner loop runs up to n times.
3. Each iteration is O(1).
4. Total time ≈ n^2.
5. Space = O(1).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation:
------------
- Subarray sum(i..j) = prefixSum[j] - prefixSum[i-1].
- If prefixSum[j] - prefixSum[i-1] == k →
  prefixSum[i-1] = prefixSum[j] - k.

Optimized Idea:
---------------
- Keep running prefixSum while iterating.
- Use a HashMap to store frequencies of prefixSum values.
- For each prefixSum:
   * Check if (prefixSum - k) exists in map.
   * If yes → all those subarrays end at current index.
   * Update map with current prefixSum.

Why This Works:
---------------
- Brute force re-computes subarray sums repeatedly.
- HashMap lets us check required prefix sum in O(1).
- Each index processed once → O(n).

Tradeoffs:
----------
- Brute force O(n^2) too slow.
- Prefix sum array helps but still O(n^2).
- HashMap solution is optimal O(n), O(n).
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Prefix Sum + HashMap)
--------------------------------------
*/

class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int prefixSum = 0;

        // Map stores: prefixSum value → frequency
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();

        // Initialize with prefixSum=0 to handle subarrays starting at index 0
        map.put(0, 1);

        for (int num : nums) {
            prefixSum += num; // update running prefix sum

            // If (prefixSum - k) exists, then subarray sum == k
            if (map.containsKey(prefixSum - k)) {
                count += map.get(prefixSum - k);
            }

            // Update frequency of current prefixSum
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

/*
Time Complexity:
---------------
- One pass through nums → O(n).
- Each HashMap get/put is O(1) average.
- Total = O(n).

Space Complexity:
----------------
- HashMap stores up to n prefix sums.
- Total = O(n).
*/


/*
--------------------------------------
MICRO-OPTIMIZED VERSION (Reduce HashMap lookups)
--------------------------------------
- Avoids calling containsKey + get separately.
- Uses a single get() to reduce hashing overhead.
*/

class SolutionOptimized {
    public int subarraySum(int[] nums, int k) {
        int count = 0, prefixSum = 0;
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();
        map.put(0, 1); // base case

        for (int num : nums) {
            prefixSum += num;

            // Use one lookup instead of containsKey + get
            Integer freq = map.get(prefixSum - k);
            if (freq != null) {
                count += freq;
            }

            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

/*
Time Complexity:
---------------
- Still O(n).

Space Complexity:
----------------
- Still O(n).

--------------------------------------
Interview Tip:
--------------
1. Start with brute force (O(n^2)).
2. Explain prefix sum relation → subarray(i..j) = prefix[j] - prefix[i-1].
3. Introduce HashMap to store prefix sums.
4. Final: O(n), O(n).
5. Mention micro-optimization (reducing lookups) to show awareness.
*/
