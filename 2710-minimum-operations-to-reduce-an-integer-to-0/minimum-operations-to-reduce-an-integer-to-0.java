/*
Microsoft | Bit Manipulation + Greedy | Medium

Problem:
--------
You are given a positive integer n. You can do the following operation any number of times:
- Add or subtract a power of 2 from n.
Return the minimum number of operations to make n = 0.

Examples:
---------
Input: n = 39 → Output: 3
   - Add 1 → 40
   - Subtract 8 → 32
   - Subtract 32 → 0

Input: n = 54 → Output: 3
   - Add 2 → 56
   - Add 8 → 64
   - Subtract 64 → 0

Constraints:
------------
- 1 <= n <= 10^5
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- At each step, try subtracting OR adding every possible power of 2 (<= n).
- Recurse until we reach 0.
- Keep track of minimum operations.

Brute Force Code (Conceptual):
-----------------
public int minOperationsBruteForce(int n) {
    if (n == 0) return 0;

    int ans = Integer.MAX_VALUE;
    for (int i = 0; (1 << i) <= n * 2; i++) { // try powers of 2
        int pow = 1 << i;
        // subtract
        if (n - pow >= 0) ans = Math.min(ans, 1 + minOperationsBruteForce(n - pow));
        // add (but limit to reasonable bound)
        ans = Math.min(ans, 1 + minOperationsBruteForce(Math.abs(n - pow)));
    }
    return ans;
}

Step-by-Step Complexity:
------------------------
1. Each step branches into many recursive calls (try all powers of 2).
2. In worst case, exponential growth of calls.
3. For n=10^5, impossible to compute.

Final Complexity:
-----------------
- Time = Exponential O(2^log n) ~ O(n)
- Space = Recursion depth O(log n)
(Too slow, impractical)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Key Insight:
------------
- We want MINIMUM steps. That hints at a **greedy or bit manipulation** approach.
- Notice: the operation is always adding/subtracting powers of 2.
- This is directly tied to the **binary representation of n**.

Observation:
------------
- If n is already a power of 2 → 1 operation.
- Otherwise, look at the **lowest set bit (LSB)**:
   - Example: n = 39 = 100111 (binary).
   - LSB = 1. Options:
      * Subtract 1 → n = 38
      * Or add 1 → n = 40, which is closer to a power of 2 (32 or 64).
   - Choose whichever gives fewer steps.

Optimized Idea:
---------------
- While n > 0:
  1. If n is a power of 2 → subtract it → done.
  2. Otherwise, check lowest set bit:
     - If that bit is isolated → subtract.
     - If consecutive ones at the bottom → better to add (carry forward).
- This ensures greedy progress toward nearest power of 2.

Why Bitwise Works Best:
-----------------------
- Each operation eliminates at least one set bit or merges consecutive ones.
- Complexity ~ number of bits (~ log n).
- Far faster than brute force.


--------------------------------------
OPTIMIZED SOLUTION (Bit Manipulation + Greedy)
--------------------------------------
*/

class Solution {
    public int minOperations(int n) {
        int operations = 0;

        while (n > 0) {
            // If n is already a power of 2 (only one bit set)
            if ((n & (n - 1)) == 0) {
                operations++;
                break;
            }

            // Find lowest set bit
            int lsb = n & -n;

            // If adding this bit removes a run of consecutive ones, it's better to add
            if ((n & (lsb << 1)) != 0) {
                n += lsb; // add power of 2
            } else {
                n -= lsb; // subtract power of 2
            }

            operations++;
        }

        return operations;
    }
}

/*
Time Complexity:
---------------
- Each operation eliminates or merges a set bit.
- Number of bits in n = log2(n).
- So worst case = O(log n).

Space Complexity:
----------------
- Only variables used → O(1).

--------------------------------------
Interview Tip:
--------------
1. Start brute force → try all power of 2 adjustments → exponential.
2. Transition: "We only need to care about binary representation."
3. Introduce greedy bit manipulation:
   - If n is power of 2 → done.
   - Otherwise, adjust lowest set bit smartly.
4. Final: O(log n), O(1).
*/
