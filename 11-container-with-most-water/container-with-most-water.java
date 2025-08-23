/*
Microsoft | Array | Two Pointers Pattern | Medium

Problem:
--------
You are given an integer array height of length n. 
There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).

Find two lines that together with the x-axis form a container, such that the container contains the most water.
Return the maximum amount of water a container can store.

Examples:
---------
Input: height = [1,8,6,2,5,4,8,3,7] → Output: 49
Input: height = [1,1] → Output: 1

Constraints:
------------
- 2 <= n <= 10^5
- 0 <= height[i] <= 10^4


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- The container is formed by choosing two lines at indices i and j.
- The water area = min(height[i], height[j]) * (j - i).
- Brute force: try all pairs (i, j), compute area, and track maximum.

Brute Force Code:
-----------------
public int maxArea(int[] height) {
    int maxArea = 0;
    for (int i = 0; i < height.length; i++) {
        for (int j = i + 1; j < height.length; j++) {
            int area = Math.min(height[i], height[j]) * (j - i);
            maxArea = Math.max(maxArea, area);
        }
    }
    return maxArea;
}

Time Complexity:
---------------
- Two nested loops → O(n^2)

Space Complexity:
----------------
- O(1) → only variables
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force checks all pairs → O(n^2), too slow for n up to 10^5.
2. Notice: 
   - Area depends on width (j - i) and shorter line height.
   - To maximize area, we want both wide width and tall min height.
3. Insight:
   - Start with widest container: (left=0, right=n-1).
   - Compute area.
   - Then, move the pointer with the smaller height inward:
       * Because area is limited by the shorter line.
       * Moving the taller one won't help (width shrinks and min height same or lower).
   - Repeat until pointers meet.
4. This guarantees we check only O(n) cases while still covering all possibilities.

Why Two Pointers is Best:
-------------------------
- Exploits problem’s geometry.
- Cuts search space intelligently.
- No extra space.
- O(n) time vs O(n^2).
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Two Pointers Approach)
--------------------------------------
*/

class Solution {
    public int maxArea(int[] height) {
        int left = 0;                  // Left pointer
        int right = height.length - 1; // Right pointer
        int maxArea = 0;

        // Move pointers towards each other
        while (left < right) {
            // Calculate current container area
            int minHeight = Math.min(height[left], height[right]);
            int width = right - left;
            int area = minHeight * width;
            maxArea = Math.max(maxArea, area);

            // Move the smaller line inward
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}

/*
Time Complexity:
---------------
- Each index visited at most once → O(n)

Space Complexity:
----------------
- Only variables → O(1)
*/
