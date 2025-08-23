/*
Microsoft | Array + HashSet | Consecutive Sequence Pattern | Medium

Problem:
--------
Given an unsorted array of integers nums, return the length of the longest 
consecutive elements sequence.

You must solve this in O(n) time.

Examples:
---------
Input: [100,4,200,1,3,2]  → Output: 4  (sequence = [1,2,3,4])
Input: [0,3,7,2,5,8,4,6,0,1] → Output: 9 (sequence = [0,1,2,3,4,5,6,7,8])
Input: [1,0,1,2] → Output: 3 (sequence = [0,1,2])

Constraints:
------------
- 0 <= nums.length <= 10^5
- -10^9 <= nums[i] <= 10^9
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

Logic:
------
1. For each number in nums:
   - Check if consecutive numbers (num+1, num+2, ...) exist in the array.
   - Count sequence length.
2. Track the maximum length found.

Code:
------
public int longestConsecutiveBruteForce(int[] nums) {
    int n = nums.length;
    int longest = 0;

    for (int i = 0; i < n; i++) {
        int currentNum = nums[i];
        int currentStreak = 1;

        // For each number, check if next consecutive exists
        int next = currentNum + 1;
        while (contains(nums, next)) {
            currentStreak++;
            next++;
        }
        longest = Math.max(longest, currentStreak);
    }
    return longest;
}

private boolean contains(int[] nums, int target) {
    for (int num : nums) {
        if (num == target) return true;
    }
    return false;
}

Step-by-step Time Complexity:
-----------------------------
1. Outer loop → n iterations.
2. For each element, inner "contains" check scans n → O(n).
3. Worst-case consecutive chain → O(n).
4. So total worst-case → O(n^3).
   - Example: nums = [1,2,3,...,n].

Space Complexity:
-----------------
- No extra DS, only variables → O(1).

Final:
------
- Time = O(n^3) ❌ (too slow for n=10^5).
- Space = O(1).
*/


/*
--------------------------------------
MOVING TO OPTIMIZATION
--------------------------------------

Observation:
------------
- Checking "does this element exist?" repeatedly is O(n).
- If we had a data structure with O(1) lookup → faster.
- HashSet gives O(1) average lookup.

Optimized Idea:
---------------
1. Store all elements in HashSet (O(n)).
2. For each number, only start sequence if it's a "sequence start":
   - A number is sequence start if (num - 1) is NOT in set.
3. From that start, count consecutive numbers (num+1, num+2, ...).
4. Track max length.

Why HashSet?
------------
- O(1) average membership check.
- Avoids O(n) scanning for each lookup.
- Ensures total O(n) complexity.

Tradeoffs:
----------
- Sorting approach works in O(n log n) → slower than O(n).
- HashSet is best as it supports O(1) lookups.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (HashSet O(n))
--------------------------------------
*/

import java.util.*;

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {
            // Only start counting if it's the beginning of a sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // Count consecutive numbers
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }

                longest = Math.max(longest, currentStreak);
            }
        }

        return longest;
    }
}

/*
Time Complexity:
---------------
1. Building set → O(n).
2. Looping through set:
   - Each element visited at most once in expansion.
   - O(n) total.
Final → O(n).

Space Complexity:
----------------
- HashSet stores all numbers → O(n).

--------------------------------------
Interview Tip:
--------------
1. Start brute force (triple nested → O(n^3)).
2. Improve: "We need faster membership checks" → HashSet O(1).
3. Optimized solution O(n), O(n).
4. Mention tradeoff: Sorting + scan also works O(n log n), 
   but HashSet is strictly better here.
*/
