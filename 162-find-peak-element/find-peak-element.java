/*
Microsoft | Array | Binary Search Pattern | Medium

Problem:
--------
A peak element is strictly greater than its neighbors.
Given an integer array nums, return the index of any one peak.
nums[-1] = nums[n] = -∞ (so edges can also be peaks).
Must run in O(log n).

Examples:
---------
Input: [1,2,3,1] → Output: 2 (3 is a peak)
Input: [1,2,1,3,5,6,4] → Output: 5 (6 is a peak, or 1 for 2)

Constraints:
------------
- 1 <= nums.length <= 1000
- -2^31 <= nums[i] <= 2^31 - 1
- nums[i] != nums[i+1] (no equal neighbors)


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- A peak is greater than neighbors.
- Simple idea: scan the array from left to right.
- For each index i:
   - Compare nums[i] with nums[i-1] and nums[i+1].
   - If greater than both → return i.
- If no internal peak found, one of the edges must be a peak (because edges compare with -∞).

Brute Force Code:
-----------------
public int findPeakElement(int[] nums) {
    int n = nums.length;
    if (n == 1) return 0;

    for (int i = 0; i < n; i++) {
        if ((i == 0 || nums[i] > nums[i-1]) &&
            (i == n-1 || nums[i] > nums[i+1])) {
            return i;
        }
    }
    return -1; // shouldn't happen
}

Time Complexity:
---------------
- O(n) scan.

Space Complexity:
----------------
- O(1).
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force is O(n). But constraint asks for O(log n).
   → Whenever you see O(log n) with arrays → think **Binary Search**.

2. Key observation:
   - If nums[mid] < nums[mid+1], then there is always a peak in the right half.
   - If nums[mid] > nums[mid+1], then there is always a peak in the left half (including mid).
   - This works because "peak" is guaranteed to exist (due to edges = -∞).

3. Why Binary Search is Best:
   - Cuts array in half each step.
   - Guaranteed to converge to a peak.
   - Only needs O(1) space.

4. Tradeoff with Other Structures:
   - Using stack/priority queue would be slower (O(n log n)).
   - Simple linear scan is O(n), not meeting O(log n) requirement.
   - Binary Search is optimal in both time and space.


--------------------------------------
OPTIMIZED SOLUTION (Binary Search)
--------------------------------------
*/

class Solution {
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // If mid element is smaller than next → peak must be right side
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                // Else, peak must be at mid or in left side
                right = mid;
            }
        }

        // left == right → peak index
        return left;
    }
}

/*
Time Complexity:
---------------
- Binary Search → O(log n)

Space Complexity:
----------------
- O(1), only variables.
*/
