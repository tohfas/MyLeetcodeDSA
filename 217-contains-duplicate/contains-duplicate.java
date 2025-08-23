/*
Microsoft | Array | Hashing (Set) / Sorting | Easy

Problem:
--------
Given an integer array nums, return true if any value appears at least twice in the array, 
and return false if every element is distinct.

Examples:
---------
Input: nums=[1,2,3,1] → Output: true
Input: nums=[1,2,3,4] → Output: false
Input: nums=[1,1,1,3,3,4,3,2,4,2] → Output: true

Constraints:
------------
- 1 <= nums.length <= 10^5
- -10^9 <= nums[i] <= 10^9
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- For each element nums[i], check if it appears again later.
- Nested loops to compare all pairs (i, j).

Brute Force Code:
-----------------
public boolean containsDuplicateBruteForce(int[] nums) {
    int n = nums.length;

    // Compare each pair (i, j)
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (nums[i] == nums[j]) {
                return true; // found duplicate
            }
        }
    }

    return false; // no duplicates
}

Step-by-Step Complexity:
------------------------
1. Outer loop runs n times.
2. Inner loop runs ~n times on average.
3. Total comparisons ≈ n(n-1)/2 = O(n^2).
4. Space = O(1) (only indices and checks).

Final Complexity:
-----------------
- Time = O(n^2) → too slow for n=10^5
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation:
------------
- We just need to detect duplicates.
- Options:
   1. Sort the array → duplicates appear adjacent → O(n log n).
   2. Use a HashSet to check if element already seen → O(1) average.

Data Structure Choice:
----------------------
- HashSet chosen because:
   - O(1) average insertion/lookup.
   - Easy to implement.
- Sorting is also valid, but O(n log n) is slower than O(n).

Tradeoffs:
----------
- Brute force O(n^2) → too slow.
- Sorting O(n log n), O(1) space if in-place.
- HashSet O(n), O(n) space → best tradeoff for speed.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (HashSet)
--------------------------------------
*/

class Solution {
    public boolean containsDuplicate(int[] nums) {
        java.util.HashSet<Integer> seen = new java.util.HashSet<>();

        for (int num : nums) {
            // If already seen, duplicate found
            if (seen.contains(num)) {
                return true;
            }
            // Otherwise add to set
            seen.add(num);
        }

        return false; // no duplicates found
    }
}

/*
Time Complexity:
---------------
- Each insertion/lookup in HashSet = O(1) average.
- Iterating over n elements = O(n).
- Total = O(n).

Space Complexity:
----------------
- HashSet stores up to n elements in worst case → O(n).

--------------------------------------
Interview Tip:
--------------
1. Start with brute force O(n^2).
2. Then suggest sorting O(n log n).
3. Conclude with HashSet O(n), O(n) as best.
4. If interviewer asks about tradeoffs:
   - Sorting uses less extra memory.
   - HashSet is fastest in practice.
*/
