/*
Microsoft | Array | Greedy (Kadane’s Variant) Pattern | Easy

Problem:
--------
You are given an array prices where prices[i] is the price of a stock on the i-th day.
You want to maximize profit by choosing one day to buy and a future day to sell.
Return the maximum profit. If no profit is possible, return 0.

Examples:
---------
Input: [7,1,5,3,6,4] → Output: 5
    Buy at 1 (day 2), sell at 6 (day 5).
Input: [7,6,4,3,1] → Output: 0
    Prices decrease every day → no profit.

Constraints:
------------
- 1 <= prices.length <= 10^5
- 0 <= prices[i] <= 10^4


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- For each buy day i, check every future sell day j.
- Profit = prices[j] - prices[i].
- Track maximum profit.

Brute Force Code:
-----------------
public int maxProfit(int[] prices) {
    int maxProfit = 0;
    for (int i = 0; i < prices.length; i++) {
        for (int j = i + 1; j < prices.length; j++) {
            maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
        }
    }
    return maxProfit;
}

Time Complexity:
---------------
- O(n^2): for each i, loop over j.

Space Complexity:
----------------
- O(1): only variables.
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force checks all pairs → O(n^2), not feasible for n=10^5.
2. Key observation:
   - To maximize profit, we want the **lowest price before today** and the **best difference with today’s price**.
3. Greedy approach:
   - Keep track of minPrice (lowest price so far).
   - At each step:
       * Compute profit = currentPrice - minPrice.
       * Update maxProfit if profit is larger.
       * Update minPrice if currentPrice is smaller.
4. Why this is best:
   - Only need the past minimum → no need for full DP array.
   - Kadane-like greedy strategy → O(n), O(1).
5. Tradeoffs:
   - Brute force O(n^2) → too slow.
   - DP array O(n) → correct but wastes space.
   - Greedy (variables only) → O(n), O(1) → best choice.


--------------------------------------
OPTIMIZED SOLUTION (Greedy with if-else)
--------------------------------------
*/
/*
class Solution1 {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }
        return maxProfit;
    }
}
*/

/*
Time Complexity: O(n) → single pass
Space Complexity: O(1) → only variables
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Greedy with Math.min/Math.max)
--------------------------------------
This version eliminates branches (`if/else`) and relies on 
JVM intrinsics for Math.min/Math.max. 
Often runs slightly faster in practice.
*/

class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        return maxProfit;
    }
}

/*
Time Complexity: O(n)
Space Complexity: O(1)
*/


/*
--------------------------------------
COMPARISON TABLE
--------------------------------------

| Approach              | Time   | Space | Notes                               |
|-----------------------|--------|-------|-------------------------------------|
| Brute Force           | O(n^2) | O(1)  | Too slow for n=10^5                 |
| DP Array              | O(n)   | O(n)  | Stores profit per day unnecessarily |
| Greedy (if-else)      | O(n)   | O(1)  | Clean, interview-friendly           |
| Greedy (Math.min/max) | O(n)   | O(1)  | Fastest in practice, branchless     |

--------------------------------------

In Interviews:
--------------
- Start by explaining brute force.
- Show optimization insight (track minPrice).
- Write Greedy O(n), O(1) solution.
- Mention: “We can also optimize branches with Math.min/max for micro-efficiency.”
- This shows both correctness & performance awareness.
*/
