/*
Microsoft | Array | Kadane’s Algorithm / Greedy Pattern | Easy

Problem:
--------
You are given an array prices where prices[i] is the price of a stock on the i-th day.  
You want to maximize profit by choosing one day to buy and another future day to sell.  
Return the maximum profit. If no profit is possible, return 0.

Examples:
---------
Input: [7,1,5,3,6,4] → Output: 5
    Buy at price=1 (day 2), sell at price=6 (day 5).
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
- Try all possible buy/sell pairs.
- For each i (buy day), check all j > i (sell day).
- Compute profit = prices[j] - prices[i].
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
- O(n^2): For each i, inner loop checks n-i values.

Space Complexity:
----------------
- O(1).
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force is O(n^2), not feasible for n up to 10^5.
2. Observation:
   - We don’t need to check all pairs.
   - To maximize profit, we want the **lowest buy price so far** and the **maximum profit from current price - minPrice**.
3. Greedy approach:
   - Traverse array once.
   - Keep track of:
       * minPrice (lowest price seen so far).
       * maxProfit (best profit so far).
   - For each day:
       * profit = prices[i] - minPrice
       * update maxProfit if profit > current maxProfit
       * update minPrice if prices[i] < minPrice
4. Data structure choice:
   - Just variables (minPrice, maxProfit).
   - O(1) space, O(n) time.
5. Why this is best:
   - We only need past minimum and current value to decide.
   - Any other structure (stack, DP array) would add overhead without benefit.
   - This is essentially a **Kadane’s Algorithm** variant.

--------------------------------------
OPTIMIZED SOLUTION (One Pass Greedy)
--------------------------------------
*/

class Solution {
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE; // track lowest price so far
        int maxProfit = 0; // track max profit

        for (int price : prices) {
            // Update minPrice if we find a lower price
            if (price < minPrice) {
                minPrice = price;
            } 
            // Else check profit if selling today
            else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;
            }
        }

        return maxProfit;
    }
}

/*
Time Complexity:
---------------
- Single pass → O(n)

Space Complexity:
----------------
- Only two variables → O(1)
*/
