/*
Microsoft | Array | Two Pointers Pattern | Easy

Problem:
--------
Given an integer array nums, move all 0's to the end of it while maintaining 
the relative order of the non-zero elements.
You must do this in-place without making a copy.

Examples:
---------
Input: [0,1,0,3,12] → Output: [1,3,12,0,0]
Input: [0] → Output: [0]

Constraints:
------------
- 1 <= nums.length <= 10^4
- -2^31 <= nums[i] <= 2^31 - 1

Follow-up:
-----------
Minimize the total number of operations done.


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- Idea: For every zero, shift all subsequent elements left, 
  then put zero at the end.
- This maintains order but repeatedly shifts elements, very costly.

Brute Force Code:
-----------------
public void moveZeroes(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
        if (nums[i] == 0) {
            // shift left
            for (int j = i; j < n - 1; j++) {
                nums[j] = nums[j + 1];
            }
            nums[n - 1] = 0;
        }
    }
}

Time Complexity:
---------------
- Worst-case: O(n^2), since each zero causes shifting.

Space Complexity:
----------------
- O(1), in-place.


--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force shifts elements repeatedly → O(n^2). Too slow for n=10^4.
2. Observation:
   - We want all non-zero elements compacted at the front.
   - Zeros should “slide” to the end automatically.
3. Greedy + Two Pointers:
   - Use a pointer `pos` to track where the next non-zero should go.
   - Traverse the array:
       * If nums[i] != 0 → put nums[i] at nums[pos], increment pos.
   - After traversal, fill the rest of array with zeros.
4. Why this is best:
   - One pass compacts non-zero elements.
   - Another pass fills zeros → O(n) total.
   - No extra space used.
5. Tradeoffs:
   - Brute force → too slow.
   - Using extra array → O(n) space, not allowed.
   - Two pointers in-place → O(n), O(1), best solution.


--------------------------------------
OPTIMIZED SOLUTION (Two Pointers)
--------------------------------------
*/

class Solution {
    public void moveZeroes(int[] nums) {
        int pos = 0; // index to place next non-zero

        // First pass: compact non-zero elements to the front
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[pos] = nums[i];
                pos++;
            }
        }

        // Second pass: fill remaining with zeros
        while (pos < nums.length) {
            nums[pos] = 0;
            pos++;
        }
    }
}

/*
Time Complexity:
---------------
- First pass O(n) + second pass O(n) = O(n)

Space Complexity:
----------------
- O(1), in-place with only a variable.
*/
