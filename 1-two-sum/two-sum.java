/*
--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

Logic:
------
- Use two nested loops to check all pairs (i, j).
- If nums[i] + nums[j] == target, return {i, j}.
- Very simple, but inefficient (O(n^2)).

Brute Force Code:
-----------------
public int[] twoSum(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[] { i, j };
            }
        }
    }
    return new int[] {}; // Not needed, problem guarantees a solution
}

Time Complexity:
---------------
- Outer loop O(n), inner loop O(n) → O(n^2)

Space Complexity:
----------------
- No extra data structures → O(1)
*/

/*
--------------------------------------
OPTIMIZED SOLUTION (Using HashMap)
--------------------------------------

Logic:
------
- Use a HashMap to store each number and its index.
- For each element nums[i]:
   1. Compute complement = target - nums[i].
   2. If complement exists in HashMap, return {map.get(complement), i}.
   3. Otherwise, store nums[i] with index i in HashMap.
- Works in O(n) time.
*/

import java.util.HashMap;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // HashMap to store number -> index
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            // If complement exists, return indices
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }

            // Otherwise, store current number with its index
            map.put(nums[i], i);
        }

        // If no solution found (not possible as per problem statement)
        throw new IllegalArgumentException("No two sum solution");
    }
}

/*
Time Complexity:
---------------
- Single traversal of array → O(n).
- Lookup/insertion in HashMap → O(1) average.
- Overall Time Complexity = O(n).

Space Complexity:
----------------
- HashMap stores up to n elements.
- Overall Space Complexity = O(n).
*/
