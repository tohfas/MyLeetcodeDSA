/*
Microsoft | Dynamic Programming | Fibonacci Pattern | Easy

Problem:
--------
You are climbing a staircase. It takes n steps to reach the top.
Each time you can climb either 1 or 2 steps. 
Return the number of distinct ways to reach the top.

Examples:
---------
Input: n = 2 → Output: 2
    (1+1, 2)
Input: n = 3 → Output: 3
    (1+1+1, 1+2, 2+1)

Constraints:
------------
1 <= n <= 45


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- At each step, you have 2 choices: climb 1 or 2 steps.
- This forms a recursion tree.
- Base cases:
   * If n == 0 → 1 way (you are already at the top).
   * If n < 0 → 0 ways.
- Recursively compute ways for (n-1) + (n-2).

Brute Force Code:
-----------------
public int climbStairs(int n) {
    if (n == 0) return 1;
    if (n < 0) return 0;
    return climbStairs(n-1) + climbStairs(n-2);
}

Time Complexity:
---------------
- Exponential: O(2^n) because of overlapping subproblems.

Space Complexity:
----------------
- Recursion depth O(n).
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force repeats the same subproblems (overlapping recursion).
   Example: climbStairs(5) computes climbStairs(3) multiple times.
2. This is a **Dynamic Programming problem**:
   - dp[i] = number of ways to reach step i.
   - Recurrence: dp[i] = dp[i-1] + dp[i-2].
3. Observing the recurrence:
   - This is essentially the **Fibonacci sequence**.
   - Ways(n) = Fib(n+1).
4. Data structure choice:
   - DP array → easy to implement, O(n) time, O(n) space.
   - But we only need last two values → optimize to O(1) space.
5. Tradeoffs:
   - Recursion (brute force) → simple but exponential.
   - DP array → clear, O(n) time, O(n) space.
   - Optimized Fibonacci (two variables) → O(n) time, O(1) space → best balance.


--------------------------------------
OPTIMIZED SOLUTION (DP → Fibonacci with O(1) space)
--------------------------------------
*/

class Solution {
    public int climbStairs(int n) {
        if (n <= 2) return n; // Base cases: n=1 → 1 way, n=2 → 2 ways

        int oneStepBefore = 2; // dp[i-1]
        int twoStepsBefore = 1; // dp[i-2]
        int currentWays = 0;

        // Iteratively build result
        for (int i = 3; i <= n; i++) {
            currentWays = oneStepBefore + twoStepsBefore;
            twoStepsBefore = oneStepBefore;
            oneStepBefore = currentWays;
        }

        return currentWays;
    }
}

/*
Time Complexity:
---------------
- Single loop up to n → O(n)

Space Complexity:
----------------
- Only 3 variables → O(1)
*/
