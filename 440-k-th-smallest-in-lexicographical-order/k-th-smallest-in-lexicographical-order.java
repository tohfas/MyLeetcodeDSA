/*
Microsoft | Trie / Lexicographical Ordering | Hard

Problem:
--------
Given two integers n and k, return the k-th smallest integer in the range [1, n] in lexicographical order.

Examples:
---------
Input: n = 13, k = 2 → Output: 10
Explanation: Lex order = [1, 10, 11, 12, 13, 2, 3, ..., 9]
2nd element = 10

Input: n = 1, k = 1 → Output: 1

Constraints:
------------
1 <= k <= n <= 10^9


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- Generate all numbers from 1 → n.
- Convert them to strings.
- Sort them lexicographically.
- Return the k-th element.

Brute Force Code:
-----------------
public int findKthNumber(int n, int k) {
    List<String> list = new ArrayList<>();
    for (int i = 1; i <= n; i++) {
        list.add(String.valueOf(i));
    }
    Collections.sort(list);
    return Integer.parseInt(list.get(k - 1));
}

Time Complexity:
---------------
- Building list: O(n)
- Sorting strings: O(n log n * L) (L = avg string length)
- For n up to 10^9 → impossible (memory + time blowup).

Space Complexity:
----------------
- O(n) to store numbers.


--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force cannot handle n up to 10^9 → infeasible.
2. Insight: Numbers in lexicographical order form a **10-ary tree (Trie-like structure)**:
   - Root has children [1,2,...,9]
   - Each node x has children [x0, x1, ..., x9] (if <= n).
   - Traversing tree in preorder gives lexicographic order.
3. Instead of generating all, we can **skip subtrees**:
   - For prefix `p`, count how many numbers exist between `p` and `p+1` within [1, n].
   - If count >= k → answer lies in this subtree (go deeper).
   - If count < k → skip this whole subtree, subtract count, move to next sibling.
4. Data structure choice:
   - Just integers, no explicit tree built.
   - Efficient calculation of "steps" between prefixes.
5. Why best:
   - We avoid constructing all numbers.
   - Time ~ O(log n * log n) because we only explore depth of prefixes.
6. Tradeoffs:
   - Brute force: O(n log n) memory heavy.
   - Building Trie explicitly: O(n), infeasible for 10^9.
   - Prefix-count skipping: O(log n * log n), O(1) space → optimal.


--------------------------------------
OPTIMIZED SOLUTION (Prefix Counting)
--------------------------------------
*/

class Solution {
    public int findKthNumber(int n, int k) {
        int curr = 1;   // start from prefix "1"
        k--;            // because we already count "1"

        while (k > 0) {
            long steps = countSteps(n, curr, curr + 1);
            
            if (steps <= k) {
                // Skip entire subtree of "curr"
                curr++;
                k -= steps;
            } else {
                // Go deeper into "curr"
                curr *= 10;
                k--;
            }
        }

        return curr;
    }

    // Count how many numbers exist between prefix1 and prefix2 within [1, n]
    private long countSteps(int n, long prefix1, long prefix2) {
        long steps = 0;
        while (prefix1 <= n) {
            steps += Math.min(n + 1L, prefix2) - prefix1;
            prefix1 *= 10;
            prefix2 *= 10;
        }
        return steps;
    }
}

/*
Time Complexity:
---------------
- Each step computes prefix counts in O(log n).
- We make at most O(log n) prefix traversals.
- Total = O((log n)^2).

Space Complexity:
----------------
- O(1), only variables.
*/
