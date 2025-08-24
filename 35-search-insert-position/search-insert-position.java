/*
Microsoft | Binary Search Pattern | Easy
---------------------------------------------------------
Problem:
--------
Given a sorted array of distinct integers and a target value, 
return the index if the target is found. 
If not, return the index where it would be if inserted in order.

You must write an algorithm with O(log n) runtime complexity.

Difficulty: Easy
Pattern: Binary Search
---------------------------------------------------------
*/

/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   - Iterate through the array from left to right.
   - If we find the target, return its index.
   - If target is smaller than current element, 
     return that index (insert position).
   - If we reach the end, target is greater than all elements,
     so return nums.length.

   =========================================================
   BRUTE FORCE CODE (COMMENTED)
   =========================================================
*/
/*
class SolutionBruteForce {
    public int searchInsert(int[] nums, int target) {
        // Traverse the array linearly
        for (int i = 0; i < nums.length; i++) {
            // Case 1: target found
            if (nums[i] == target) {
                return i;
            }
            // Case 2: target should be inserted before nums[i]
            if (nums[i] > target) {
                return i;
            }
        }
        // Case 3: target is larger than all elements
        return nums.length;
    }
}

/*
------------------------------------------------------------
Step-by-step Complexity (Brute Force):
- Looping through nums: O(n).
- No extra space used except a few variables: O(1).
So,
Time Complexity = O(n)
Space Complexity = O(1)

Why inefficient?
----------------
Because the array is sorted. Instead of scanning linearly,
we can use **binary search** to achieve O(log n).
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH (Binary Search)
   =========================================================
   Logic:
   ------
   - Since nums is sorted, we use Binary Search.
   - Maintain left=0, right=n-1.
   - While left <= right:
       * Find mid.
       * If nums[mid] == target → return mid.
       * If nums[mid] < target → move left = mid+1.
       * If nums[mid] > target → move right = mid-1.
   - If not found, the correct insert position is `left`.

   Why does `left` give insert position?
   -------------------------------------
   - When loop ends, left will point to the first index
     where nums[left] >= target.
   - If all numbers < target → left = nums.length.

   =========================================================
   OPTIMIZED CODE (COMMENTED)
   =========================================================
*/

class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        // Standard Binary Search
        while (left <= right) {
            int mid = left + (right - left) / 2; // avoid overflow

            if (nums[mid] == target) {
                return mid; // target found
            } else if (nums[mid] < target) {
                left = mid + 1; // target lies to the right
            } else {
                right = mid - 1; // target lies to the left
            }
        }

        // If not found, left is the insert position
        return left;
    }
}

/*
=========================================================
TIME & SPACE COMPLEXITY
=========================================================
Brute Force:
------------
Time: O(n) (linear scan)
Space: O(1)

Optimized (Binary Search):
--------------------------
Time: O(log n)  (because search space halves each step)
Space: O(1)     (just variables left, right, mid)

=========================================================
INTERVIEW THINKING FLOW
=========================================================
1. Start with brute force: linear scan → O(n).
   Realize it’s wasteful since array is sorted.
2. Optimization idea: binary search reduces time to O(log n).
3. Key reasoning: insert position is `left` after binary search,
   because left ends up where target should be placed.
4. Data Structure Choice:
   - No need for extra space like ArrayList/HashMap.
   - Array indexing + binary search is the best tradeoff.
=========================================================
*/
