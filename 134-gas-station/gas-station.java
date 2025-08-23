/*
Microsoft | Array | Greedy + Prefix Sum Pattern | Medium

Problem:
--------
There are n gas stations along a circular route, where gas[i] is the gas available at station i, 
and cost[i] is the gas required to travel from i to (i+1). 
You start with an empty tank and must find the starting gas station index 
from which you can travel the entire circuit once. 
If no such start exists, return -1. 
It is guaranteed that if a solution exists, it is unique.

Examples:
---------
Input: gas=[1,2,3,4,5], cost=[3,4,5,1,2] → Output: 3
Input: gas=[2,3,4], cost=[3,4,3] → Output: -1

Constraints:
------------
- n == gas.length == cost.length
- 1 <= n <= 10^5
- 0 <= gas[i], cost[i] <= 10^4
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION (Step by Step)
--------------------------------------

How to Think Brute Force:
-------------------------
- Try starting from each station i.
- Simulate the trip around the circle:
   1. Start with tank = 0.
   2. At each step: tank += gas[j] - cost[j].
   3. If tank < 0 → cannot continue, break.
   4. If we complete the full cycle → return i.
- If no start works → return -1.

Brute Force Code:
-----------------
public int canCompleteCircuit(int[] gas, int[] cost) {
    int n = gas.length;

    for (int start = 0; start < n; start++) {
        int tank = 0;
        boolean valid = true;

        // try to travel n steps from this start
        for (int step = 0; step < n; step++) {
            int idx = (start + step) % n; // circular
            tank += gas[idx] - cost[idx];
            if (tank < 0) {
                valid = false;
                break; // cannot complete from this start
            }
        }

        if (valid) return start;
    }

    return -1;
}

Step-by-Step Complexity:
------------------------
1. Outer loop tries all n stations.
2. For each start, simulate n steps → O(n).
3. Total = O(n^2).
   Example: n=10^5 → impossible in time.
4. Space = O(1).

Final Complexity:
-----------------
- Time = O(n^2)
- Space = O(1)
*/


/*
--------------------------------------
MOVING TOWARDS OPTIMIZATION
--------------------------------------

Observation 1:
--------------
- If totalGas < totalCost → impossible (return -1).
- Because across full circle, you cannot have enough fuel.

Observation 2:
--------------
- If starting from i fails at some j (tank < 0):
   → None of the stations between i and j can be a valid start.
   Why? Because they would all run out of fuel even earlier.
   So we can skip directly to j+1.

Greedy Strategy:
----------------
1. Traverse once, track:
   - totalTank = total gas - total cost across all stations.
   - currTank = running balance.
   - start = candidate starting index.
2. If currTank < 0 → reset currTank=0, start=next station.
3. After one pass:
   - If totalTank >= 0 → start is the answer.
   - Else → -1.

Why Greedy is Best:
-------------------
- We skip useless candidates.
- One pass only → O(n).
- Space O(1).
- Tradeoff:
   - Brute force: O(n^2) (too slow).
   - Greedy: O(n), O(1) → optimal.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Greedy O(n))
--------------------------------------
*/

class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;

        int totalTank = 0;   // total net gas across all stations
        int currTank = 0;    // net gas from current start candidate
        int start = 0;       // candidate start index

        for (int i = 0; i < n; i++) {
            int balance = gas[i] - cost[i];
            totalTank += balance;   // global balance
            currTank += balance;    // running balance

            // If we fail here, reset start
            if (currTank < 0) {
                start = i + 1;      // next station as candidate
                currTank = 0;       // reset running tank
            }
        }

        // If global gas >= cost, solution exists at start
        return (totalTank >= 0) ? start : -1;
    }
}

/*
Time Complexity:
---------------
- Single traversal of n stations → O(n).

Space Complexity:
----------------
- Only variables totalTank, currTank, start → O(1).

--------------------------------------
Interview Tip:
--------------
- First explain brute force (simulate each start).
- Show O(n^2) → infeasible.
- Insight: "If total gas < total cost, impossible."
- Then Greedy: reset start when tank < 0, skip useless candidates.
- Final: O(n), O(1).
*/
