/*
Microsoft | Array | Binary Search Pattern | Hard

Problem:
--------
Given two sorted arrays nums1 and nums2 of size m and n respectively, 
return the median of the two sorted arrays.
The overall runtime complexity should be O(log (m+n)).

Examples:
---------
Input: nums1 = [1,3], nums2 = [2]      → Output: 2.0
Input: nums1 = [1,2], nums2 = [3,4]    → Output: 2.5

Constraints:
------------
- nums1.length == m, nums2.length == n
- 0 <= m, n <= 1000
- 1 <= m + n <= 2000
- -10^6 <= nums[i] <= 10^6


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

Logic:
------
1. Merge the two sorted arrays into one sorted array.
2. If total length is odd → return middle element.
3. If even → return average of two middle elements.

Brute Force Code:
-----------------
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int m = nums1.length, n = nums2.length;
    int[] merged = new int[m + n];
    int i = 0, j = 0, k = 0;

    // Merge step of merge sort
    while (i < m && j < n) {
        if (nums1[i] < nums2[j]) {
            merged[k++] = nums1[i++];
        } else {
            merged[k++] = nums2[j++];
        }
    }
    while (i < m) merged[k++] = nums1[i++];
    while (j < n) merged[k++] = nums2[j++];

    int total = m + n;
    if (total % 2 == 1) {
        return merged[total / 2];
    } else {
        return (merged[(total / 2) - 1] + merged[total / 2]) / 2.0;
    }
}

Time Complexity:
---------------
- Merge step → O(m + n)
- Access median → O(1)
- Total → O(m + n)

Space Complexity:
----------------
- Extra merged array → O(m + n)
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Binary Search Partition)
--------------------------------------

Logic:
------
- We want to partition both arrays such that:
   - Left side has (m+n+1)/2 elements
   - All elements in left ≤ all elements in right
- Use binary search on the smaller array.
- At each step:
   - Partition nums1 at index i
   - Partition nums2 at index j = (m+n+1)/2 - i
   - Ensure nums1[i-1] ≤ nums2[j] and nums2[j-1] ≤ nums1[i]
- If condition holds:
   - If total length odd → median = max(left parts)
   - If even → median = (max(left parts) + min(right parts)) / 2
- Adjust binary search boundaries otherwise.
- Runs in O(log(min(m, n))).

*/

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length, n = nums2.length;
        int low = 0, high = m;

        while (low <= high) {
            int partition1 = (low + high) / 2;
            int partition2 = (m + n + 1) / 2 - partition1;

            // Handle edges with sentinel values
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            // Correct partition found
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                } else {
                    return Math.max(maxLeft1, maxLeft2);
                }
            }
            // Move binary search boundaries
            else if (maxLeft1 > minRight2) {
                high = partition1 - 1; // move left
            } else {
                low = partition1 + 1;  // move right
            }
        }

        throw new IllegalArgumentException("Input arrays not sorted properly.");
    }
}

/*
Time Complexity:
---------------
- Binary search runs on smaller array → O(log(min(m, n)))
- Partition checks are O(1).
- Overall Time Complexity = O(log(min(m, n)))

Space Complexity:
----------------
- Only variables, no extra arrays.
- Overall Space Complexity = O(1).
*/
