/*
Microsoft | Array + Stack | Monotonic Stack Pattern | Hard

Problem:
--------
Given an array of integers heights representing the histogram's bar heights (width = 1 each),
return the area of the largest rectangle in the histogram.

Examples:
---------
Input: [2,1,5,6,2,3] → Output: 10
    The largest rectangle is formed by bars of height 5 and 6 → area = 2 * 5 = 10.

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
   - Expand left until a smaller height is found.
   - Expand right until a smaller height is found.
   - Width = right - left - 1
   - Area = height[i] * width
- Track maximum across all i.

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

Step-by-Step Complexity Analysis:
---------------------------------
1. Outer loop runs n times.
2. For each i, we expand left O(n) and right O(n) in worst case.
   → For array of all equal heights, each expansion touches almost n bars.
3. Total = O(n^2).

Space Complexity:
-----------------
- Only variables left, right, maxArea → O(1).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force expands left and right repeatedly → O(n^2).
2. Observation:
   - To compute max rectangle, we need the "first smaller element on left and right".
   - Instead of recomputing every time, we can precompute using a stack.
3. Monotonic Stack:
   - Use a stack to maintain increasing heights.
   - When a bar is smaller than stack top, we pop from stack:
       * The popped bar defines a rectangle.
       * Width = (currentIndex - previousSmallerIndex - 1).
   - Push current index into stack.
   - At end, process remaining stack bars.
4. Why Stack is Best:
   - Each bar is pushed and popped at most once → O(n).
   - Stack efficiently finds left and right smaller boundaries.
5. Tradeoffs:
   - Brute force → O(n^2).
   - Precomputing left/right arrays separately → O(n) but extra space.
   - Stack → O(n), O(n) but elegant and minimal.


--------------------------------------
OPTIMIZED SOLUTION (Monotonic Stack)
--------------------------------------
*/

class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;

        // Stack will store indices of increasing heights
        java.util.Stack<Integer> stack = new java.util.Stack<>();

        for (int i = 0; i <= n; i++) {
            // When i == n, we treat current height as 0 to flush stack
            int currHeight = (i == n) ? 0 : heights[i];

            // While stack not empty and current height is smaller than top of stack
            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int height = heights[stack.pop()]; // height of popped bar
                int width;

                if (stack.isEmpty()) {
                    // means popped bar was smallest till now
                    width = i;
                } else {
                    // width = distance between current i and new top
                    width = i - stack.peek() - 1;
                }

                int area = height * width;
                maxArea = Math.max(maxArea, area);
            }

            // push current index
            stack.push(i);
        }

        return maxArea;
    }
}

/*
Time Complexity:
---------------
- Each bar is pushed once and popped once from the stack.
- O(n) total operations.
- So time = O(n).

Space Complexity:
----------------
- Stack stores indices, at most n elements.
- O(n) space.

--------------------------------------
Interview Tip:
--------------
- Always explain brute force first (expand left & right).
- Show why it's O(n^2).
- Transition: we only need left smaller and right smaller → use monotonic stack.
- Final solution O(n), O(n).
*/
