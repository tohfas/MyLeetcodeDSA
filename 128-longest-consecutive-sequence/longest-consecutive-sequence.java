/*
Microsoft | Arrays + HashSet (Sequence Building Pattern) | Medium

Problem:
--------
Given an unsorted array of integers nums, return the length of the longest 
consecutive elements sequence.

We must write an algorithm that runs in O(n) time.

--------------------------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------------------------

Logic:
------
1. Sort the array (since we want consecutive numbers).
2. Scan the sorted array while counting the length of the current consecutive sequence.
3. Track the maximum length seen so far.

Code (Brute Force):
-------------------
public int longestConsecutiveBruteForce(int[] nums) {
    if (nums.length == 0) return 0;

    Arrays.sort(nums);  // O(n log n)

    int maxLen = 1;
    int currLen = 1;

    for (int i = 1; i < nums.length; i++) {
        if (nums[i] == nums[i-1]) continue;       // skip duplicates
        else if (nums[i] == nums[i-1] + 1) {
            currLen++;                            // consecutive continues
        } else {
            maxLen = Math.max(maxLen, currLen);   // reset sequence
            currLen = 1;
        }
    }
    maxLen = Math.max(maxLen, currLen);
    return maxLen;
}

Step-by-step Complexity:
------------------------
- Sorting: O(n log n)
- One scan: O(n)
- Total Time Complexity: O(n log n)
- Space Complexity: O(1) (ignoring sorting cost)

Why not optimal?
----------------
Because requirement is strictly O(n). Sorting gives O(n log n).
We need something faster: Hashing.

--------------------------------------------------------
OPTIMIZED SOLUTION (HashSet + Sequence Building)
--------------------------------------------------------

Logic:
------
1. Insert all numbers into a HashSet for O(1) lookups.
2. For each number, check if it is a **sequence start**:
   - A sequence start is a number `x` such that `x-1` is not in the set.
   - That means `x` is the first element of some sequence.
3. If it is a start, keep checking x+1, x+2, ... until the sequence breaks.
4. Track maximum sequence length.

Why this works:
---------------
- Each number is processed only once in its sequence expansion.
- Ensures O(n) total, because every element belongs to exactly one sequence traversal.

Tradeoffs vs Other Data Structures:
-----------------------------------
- Could try sorting → O(n log n).
- Could try Union-Find (DSU) → harder, O(n α(n)).
- HashSet is best here: O(n) avg with clear intuition.

--------------------------------------------------------
OPTIMIZED JAVA CODE
--------------------------------------------------------
*/

import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        // Step 1: Insert all numbers into a HashSet for O(1) lookups
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int longest = 0;

        // Step 2: Iterate through each number
        for (int num : set) {
            // Only start counting if num is the beginning of a sequence
            if (!set.contains(num - 1)) {
                int currNum = num;
                int currLen = 1;

                // Step 3: Expand the sequence forward
                while (set.contains(currNum + 1)) {
                    currNum++;
                    currLen++;
                }

                // Step 4: Update global maximum
                longest = Math.max(longest, currLen);
            }
        }

        return longest;
    }
}

/*
--------------------------------------------------------
TIME & SPACE COMPLEXITY
--------------------------------------------------------

Brute Force:
------------
- Time: O(n log n) because of sorting.
- Space: O(1).

Optimized:
----------
- Time: O(n)
   * Insert into HashSet: O(n).
   * Each number expanded only once: O(n).
   * Total: O(n).
- Space: O(n) for HashSet.

--------------------------------------------------------
Interview Thought Process:
--------------------------
1. Start with brute force: sorting + scanning O(n log n).
2. Recognize O(n) requirement → sorting not allowed.
3. Think about checking consecutiveness quickly → HashSet O(1).
4. Key trick: Only start from numbers where `num-1` doesn’t exist,
   to avoid recomputation.
5. This guarantees linear time.

--------------------------------------------------------
*/
