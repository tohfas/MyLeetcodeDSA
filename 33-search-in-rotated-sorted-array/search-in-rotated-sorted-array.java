/*
Microsoft | Array | Binary Search Pattern | Medium

Problem:
--------
There is an integer array nums sorted in ascending order (with distinct values).
It is rotated at some pivot. 
Return the index of target if present, else -1.
Must solve in O(log n).

Examples:
---------
Input: nums = [4,5,6,7,0,1,2], target = 0 → Output: 4
Input: nums = [4,5,6,7,0,1,2], target = 3 → Output: -1
Input: nums = [1], target = 0 → Output: -1

Constraints:
------------
- 1 <= nums.length <= 5000
- -10^4 <= nums[i] <= 10^4
- All values are unique.
- nums is sorted but possibly rotated.
- -10^4 <= target <= 10^4


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- If array wasn’t rotated → simple binary search.
- With rotation, the array is "split" into two sorted halves.
- A brute force way: just linearly scan the array and return index if target found.

Brute Force Code:
-----------------
public int search(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) {
            return i;
        }
    }
    return -1;
}

Time Complexity:
---------------
- O(n), since we may scan all elements.

Space Complexity:
----------------
- O(1), only variables.
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force scans everything → O(n).
   But problem explicitly demands O(log n).

2. "O(log n)" → immediately think of **Binary Search**.

3. Problem: array is rotated, but still "partially sorted":
   - At least one half (left or right of mid) will always be sorted.
   - We can detect which half is sorted and decide where to search.

4. Approach:
   - Compute mid.
   - If nums[mid] == target → return mid.
   - If left half is sorted (nums[left] <= nums[mid]):
        * If target is within [nums[left], nums[mid]] → search left.
        * Else → search right.
   - Else (right half is sorted):
        * If target is within [nums[mid], nums[right]] → search right.
        * Else → search left.

Why Binary Search is Best Here:
-------------------------------
- Array property (partially sorted due to rotation) makes binary search possible.
- Cuts search space in half each time → O(log n).
- Chosen data structure: just array indices; no extra space.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Binary Search in Rotated Array)
--------------------------------------
*/

class Solution {
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // If found target
            if (nums[mid] == target) {
                return mid;
            }

            // Check if left half is sorted
            if (nums[left] <= nums[mid]) {
                // If target lies in left half
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } 
            // Else, right half is sorted
            else {
                if (target > nums[mid] && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1; // Not found
    }
}

/*
Time Complexity:
---------------
- Each step halves the search space.
- O(log n)

Space Complexity:
----------------
- No extra data structures.
- O(1)
*/
