/*
Microsoft | Array | Binary Search (Rotated Array Pattern) | Medium

Problem:
--------
Given a rotated sorted array of unique elements, find the minimum element.
You must solve it in O(log n) time.

Examples:
---------
Input: [3,4,5,1,2] → Output: 1
Input: [4,5,6,7,0,1,2] → Output: 0
Input: [11,13,15,17] → Output: 11

Constraints:
------------
- n == nums.length
- 1 <= n <= 5000
- -5000 <= nums[i] <= 5000
- All elements are unique.
- Array is sorted and rotated between 1 and n times.
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

Logic:
------
- Scan the entire array linearly.
- The minimum is simply the smallest element encountered.

Code:
------
public int findMinBruteForce(int[] nums) {
    int minVal = nums[0];
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < minVal) {
            minVal = nums[i];
        }
    }
    return minVal;
}

Step-by-step Time Complexity:
-----------------------------
1. Single loop runs over all n elements → O(n).
2. Comparisons constant time.

Final Complexity:
-----------------
- Time = O(n)
- Space = O(1)

Why not optimal?
----------------
- Problem explicitly asks for O(log n).
- Since array is rotated sorted → we can use Binary Search.
*/


/*
--------------------------------------
MOVING TO OPTIMIZATION
--------------------------------------

Observation:
------------
- Array is sorted but rotated.
- The "minimum element" is the pivot point of rotation.
- Binary Search can locate pivot in O(log n).

Optimized Idea:
---------------
- Use two pointers: left, right.
- While left < right:
  - Find mid.
  - If nums[mid] > nums[right] → min is in right half.
  - Else → min is in left half (including mid).
- Finally, nums[left] will be the minimum.

Why Binary Search?
------------------
- Array is sorted (with rotation).
- The decision of which side to search can be determined using nums[mid] and nums[right].
- This guarantees O(log n).

Tradeoffs:
----------
- Brute force is simpler but O(n).
- Binary Search reduces to O(log n) → best possible for this problem.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Binary Search)
--------------------------------------
*/

class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        // Binary search loop
        while (left < right) {
            int mid = left + (right - left) / 2;

            // If mid element > right element,
            // then the minimum lies in the right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } 
            // Else the minimum lies in the left half (including mid)
            else {
                right = mid;
            }
        }

        // At the end, left == right → index of minimum element
        return nums[left];
    }
}

/*
Time Complexity:
---------------
- Binary Search halves search space each time → O(log n).

Space Complexity:
----------------
- Only pointers left, right, mid → O(1).

--------------------------------------
Interview Tip:
--------------
1. Start with brute force: scan entire array O(n).
2. State constraint: must do O(log n).
3. Transition: "Since array is rotated sorted, binary search helps locate pivot (minimum)."
4. Optimized solution O(log n), O(1).
*/
