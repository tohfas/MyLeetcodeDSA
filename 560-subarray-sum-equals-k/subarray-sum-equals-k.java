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
- A subarray is contiguous. 
- So, try every possible subarray [i..j]:
   1. Fix start index i.
   2. Expand end index j.
   3. Compute sum of nums[i..j].
   4. If sum == k → count it.
- Return total count.

Brute Force Code:
-----------------
public int subarraySumBruteForce(int[] nums, int k) {
    int n = nums.length;
    int count = 0;

    // Try every start index
    for (int i = 0; i < n; i++) {
        int sum = 0;
        // Expand subarray from i to j
        for (int j = i; j < n; j++) {
            sum += nums[j];  // accumulate sum
            if (sum == k) {
                count++;     // found a valid subarray
            }
        }
    }
    return count;
}

Step-by-Step Complexity:
------------------------
1. Outer loop runs n times.
2. Inner loop runs up to n times.
3. Each iteration updates sum in O(1).
4. Total = O(n^2).

Space:
------
- Only counters and sum variable → O(1).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Problem with Brute Force:
-------------------------
- O(n^2) is too slow for n=20,000 (≈ 400 million operations).

Observation:
------------
- If prefixSum[j] = sum of nums[0..j], 
  then subarray sum from i..j = prefixSum[j] - prefixSum[i-1].
- We want: prefixSum[j] - prefixSum[i-1] = k
  → prefixSum[i-1] = prefixSum[j] - k.

Greedy HashMap Idea:
--------------------
- Maintain running prefix sum.
- Store in a HashMap: {prefixSumValue : frequency}.
- For each index j:
   1. Compute prefixSum.
   2. Check if (prefixSum - k) exists in map → 
      means a subarray ending here has sum = k.
   3. Add frequency to count.
   4. Update map with prefixSum.

Why HashMap is Best:
--------------------
- Brute force checks O(n^2) subarrays explicitly.
- HashMap lets us find required "prefixSum[i-1]" in O(1).
- Time reduces to O(n).
- Space O(n) for prefix frequencies.

Tradeoffs:
----------
- Prefix array alone helps with O(n^2).
- HashMap balances both speed and memory.
- Best solution is O(n), O(n).
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Prefix Sum + HashMap O(n))
--------------------------------------
*/

class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int prefixSum = 0;

        // Map stores prefixSum value → frequency
        java.util.HashMap<Integer, Integer> map = new java.util.HashMap<>();

        // Initialize with 0 prefix sum (important for subarray starting at index 0)
        map.put(0, 1);

        for (int num : nums) {
            prefixSum += num;  // update running prefix sum

            // Check if (prefixSum - k) exists
            if (map.containsKey(prefixSum - k)) {
                count += map.get(prefixSum - k);
            }

            // Update map with current prefixSum
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}

/*
Time Complexity:
---------------
- Single pass over nums → O(n).
- Each map operation (get/put) → O(1) average.
- Total = O(n).

Space Complexity:
----------------
- HashMap stores at most n prefix sums → O(n).

--------------------------------------
Interview Tip:
--------------
- Always start with brute force → O(n^2).
- Then say: "I realized prefix sums let me compute subarray sums faster."
- Transition: prefix sum + hashmap counts → O(n).
- Key insight: subarray(i..j) = prefix[j] - prefix[i-1].
*/
