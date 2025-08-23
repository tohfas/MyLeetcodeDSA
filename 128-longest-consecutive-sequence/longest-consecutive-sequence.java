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
1. For each number nums[i]:
   - Check whether nums[i] + 1, nums[i] + 2, ... exist in the array.
   - Count streak length.
2. Track maximum streak length.

Code:
------
public int longestConsecutiveBruteForce(int[] nums) {
    int n = nums.length;
    int longest = 0;

    for (int i = 0; i < n; i++) {
        int currentNum = nums[i];
        int currentStreak = 1;

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

Step-by-step Complexity:
------------------------
1. Outer loop → O(n).
2. contains() inside while → O(n).
3. Worst case for each element → O(n).
4. Total → O(n^3) ❌ (too slow for n=10^5).

Space:
------
- Only variables → O(1).
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (HashSet) — O(n)
--------------------------------------

Logic:
------
- Store all nums in HashSet for O(1) lookup.
- For each num:
  - Only start counting if (num - 1) is not in set → ensures we only start at sequence beginnings.
  - Count consecutive streak (num+1, num+2, …).
- Track max streak length.

Why HashSet?
------------
- O(1) membership check on average.
- Each number visited once in expansion → O(n).

Code:
------
*/

import java.util.*;

class SolutionHashSet {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int longest = 0;

        for (int num : set) {
            if (!set.contains(num - 1)) { // start of a sequence
                int current = num;
                int streak = 1;

                while (set.contains(current + 1)) {
                    current++;
                    streak++;
                }

                longest = Math.max(longest, streak);
            }
        }

        return longest;
    }
}

/*
Complexity:
-----------
- Time = O(n) (build set + traverse set, each element checked once).
- Space = O(n) (set stores all elements).

⚠ Note: Although O(n), HashSet in Java can be slower due to boxing/unboxing
and iterator overhead, leading to lower runtime percentile.
*/


/*
--------------------------------------
PRACTICAL SOLUTION (Sorting + Scan) — O(n log n)
--------------------------------------

Logic:
------
- Sort nums.
- Traverse once, count consecutive streaks.
- Skip duplicates.
- Track longest streak.

Why Sorting?
------------
- Sorting is O(n log n), but Arrays.sort() in Java is highly optimized in C-level code.
- Avoids HashSet overhead (no boxing/unboxing).
- Often runs faster in practice on LeetCode.

Code:
------
*/

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums); // O(n log n)
        int longest = 1;
        int streak = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                continue; // skip duplicates
            } else if (nums[i] == nums[i - 1] + 1) {
                streak++;
            } else {
                longest = Math.max(longest, streak);
                streak = 1;
            }
        }

        return Math.max(longest, streak);
    }
}

/*
Complexity:
-----------
- Time = O(n log n) (sorting dominates).
- Space = O(1) (ignoring sort’s internal recursion stack).

--------------------------------------
Interview Roadmap:
------------------
1. Brute Force: O(n^3) → clearly too slow.
2. Optimal: HashSet O(n) → theoretically best.
3. Tradeoff: Sorting O(n log n) → faster in Java runtime due to optimized sort vs HashSet overhead.
4. Mention both approaches: shows depth of thought in interviews.
*/
