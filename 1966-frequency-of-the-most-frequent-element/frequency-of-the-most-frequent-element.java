/*
Microsoft | Array + Sorting + Sliding Window | Medium

Problem:
--------
You are given an integer array nums and an integer k.
In one operation, you can choose an index of nums and increment the element at that index by 1.
Return the maximum possible frequency of an element after performing at most k operations.

Examples:
---------
Input: nums=[1,2,4], k=5 → Output: 3
   Explanation: Increment 1 → 4 (3 times), 2 → 4 (2 times). Array = [4,4,4]. Frequency=3.

Input: nums=[1,4,8,13], k=5 → Output: 2
   Explanation: Best frequency = 2 (make either two 4's, two 8's, or two 13's).

Input: nums=[3,9,6], k=2 → Output: 1

Constraints:
------------
- 1 <= nums.length <= 10^5
- 1 <= nums[i] <= 10^5
- 1 <= k <= 10^5
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- Try to make each element the maximum frequency element.
- For each candidate target:
  1. Count cost = sum of (target - smaller elements).
  2. If cost <= k → update max frequency.
- Return the largest possible frequency.

Brute Force Code:
-----------------
public int maxFrequencyBruteForce(int[] nums, int k) {
    int n = nums.length;
    int maxFreq = 1;

    for (int i = 0; i < n; i++) {
        int freq = 1;
        for (int j = 0; j < n; j++) {
            if (nums[j] <= nums[i] && j != i) {
                int cost = nums[i] - nums[j];
                if (cost <= k) {
                    k -= cost;
                    freq++;
                }
            }
        }
        maxFreq = Math.max(maxFreq, freq);
    }

    return maxFreq;
}

Step-by-Step Complexity:
------------------------
1. Outer loop picks a target element O(n).
2. Inner loop checks all other elements O(n).
3. Cost computed per comparison.
4. Total = O(n^2).
   For n=10^5 → 10^10 operations (too slow).
5. Space = O(1).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Problem with Brute Force:
-------------------------
- Repeatedly recalculating costs for each target.

Observation:
------------
- If array is sorted, then:
  - To make elements in a window equal to the largest element in the window,
    cost = (windowSize * targetValue) - sum(window).

Optimized Idea:
---------------
- Sort nums.
- Use sliding window:
  1. Keep right pointer expanding.
  2. Compute cost of making all elements in window equal to nums[right].
  3. If cost > k → shrink window from left.
  4. Track max window size.
- This ensures minimal operations counted efficiently.

Why This is Best:
-----------------
- Sorting = O(n log n).
- Sliding window pass = O(n).
- Total = O(n log n).
- Better than O(n^2).
- Space O(1) extra.


--------------------------------------
OPTIMIZED SOLUTION (Sorting + Sliding Window)
--------------------------------------
*/

import java.util.Arrays;

class Solution {
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums); // Step 1: sort the array

        int n = nums.length;
        long sum = 0; // running window sum
        int left = 0; // left pointer of window
        int maxFreq = 1;

        for (int right = 0; right < n; right++) {
            sum += nums[right]; // expand window

            // Cost = (windowSize * targetValue) - sum(window)
            // If cost > k → shrink window from left
            while ((long) nums[right] * (right - left + 1) - sum > k) {
                sum -= nums[left];
                left++;
            }

            // update max frequency
            maxFreq = Math.max(maxFreq, right - left + 1);
        }

        return maxFreq;
    }
}

/*
Time Complexity:
---------------
1. Sorting = O(n log n).
2. Sliding window = O(n).
Total = O(n log n).

Space Complexity:
----------------
- Sorting in-place O(1) (ignoring sort overhead).
- Extra variables only O(1).

--------------------------------------
Interview Tip:
--------------
1. Start brute force (O(n^2)).
2. Transition: "If sorted, we can calculate costs efficiently."
3. Introduce sliding window cost formula.
4. Conclude with O(n log n), O(1).
*/
