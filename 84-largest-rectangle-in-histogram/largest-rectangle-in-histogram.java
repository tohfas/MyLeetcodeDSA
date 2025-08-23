/*
Microsoft | Array + Stack | Monotonic Stack Pattern | Hard

Problem:
--------
You are given an array of integers heights representing the histogram's bar heights (width = 1 each).
Return the area of the largest rectangle in the histogram.

Examples:
---------
Input: [2,1,5,6,2,3] → Output: 10
    Largest rectangle = 5 and 6 → width=2, area=10.
Input: [2,4] → Output: 4
    Largest rectangle = single bar of height 4.

Constraints:
------------
- 1 <= heights.length <= 10^5
- 0 <= heights[i] <= 10^4
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- For each bar at index i:
  1. Expand left until a smaller bar is found.
  2. Expand right until a smaller bar is found.
  3. Width = right - left - 1
  4. Area = heights[i] * width
- Track the maximum area.

Brute Force Code:
-----------------
public int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int maxArea = 0;

    for (int i = 0; i < n; i++) {
        int height = heights[i];

        // expand left
        int left = i;
        while (left > 0 && heights[left - 1] >= height) {
            left--;
        }

        // expand right
        int right = i;
        while (right < n - 1 && heights[right + 1] >= height) {
            right++;
        }

        int width = right - left + 1;
        int area = height * width;
        maxArea = Math.max(maxArea, area);
    }
    return maxArea;
}

Step-by-Step Complexity:
------------------------
1. Outer loop = n iterations.
2. For each i, left expansion O(n), right expansion O(n).
3. Worst case (all equal heights): O(n^2).
4. Space = O(1) (only variables).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force re-expands left and right for every bar → O(n^2).
2. Observation:
   - We only need the **nearest smaller to left (NSL)** and **nearest smaller to right (NSR)**.
   - This gives width directly without re-expanding.
3. Data Structure Choice:
   - Use a **monotonic increasing stack**:
     * Push indices of bars while they increase.
     * When we see a smaller height, pop and compute area.
     * Width = (currentIndex - previousSmallerIndex - 1).
4. Tradeoffs:
   - Arrays for NSL/NSR also work → O(n), O(n).
   - But stack gives elegant one-pass O(n).
5. Why Best:
   - Each bar pushed/popped once → O(n).
   - Memory O(n).
   - This is optimal for this problem.


--------------------------------------
OPTIMIZED SOLUTION (Monotonic Stack with ArrayDeque)
--------------------------------------
*/

class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;

        // Use ArrayDeque instead of Stack for performance (Stack is synchronized and slower)
        java.util.ArrayDeque<Integer> stack = new java.util.ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            int currHeight = (i == n) ? 0 : heights[i]; // sentinel 0 at the end to flush stack

            // When current bar is smaller than top of stack → compute area
            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                int area = height * width;
                maxArea = Math.max(maxArea, area);
            }

            stack.push(i);
        }

        return maxArea;
    }
}

/*
Time Complexity:
---------------
- Each bar is pushed and popped at most once → O(n).
- Loop runs n times → O(n).
- Total = O(n).

Space Complexity:
----------------
- Stack stores indices, at most n elements → O(n).
*/


/*
--------------------------------------
COMPARISON TABLE
--------------------------------------

| Approach             | Time   | Space | Notes                                      |
|----------------------|--------|-------|--------------------------------------------|
| Brute Force          | O(n^2) | O(1)  | Expands left & right for every bar         |
| Precompute NSL/NSR   | O(n)   | O(n)  | Two stacks/arrays, more space              |
| Monotonic Stack      | O(n)   | O(n)  | Elegant, optimal, best practical approach  |

--------------------------------------

Interview Tip:
--------------
- Start with brute force → show O(n^2).
- Explain the need for nearest smaller elements.
- Introduce monotonic stack → O(n), O(n).
- Mention implementation detail: prefer ArrayDeque over Stack in Java for speed.
*/
