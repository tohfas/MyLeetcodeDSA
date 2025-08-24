/*
Microsoft | String Frequency Analysis | Easy
----------------------------------------------------
Problem:
--------
Given a string s, return the maximum difference:
    diff = freq(a1) - freq(a2)
Where:
    - a1 has an ODD frequency
    - a2 has a NON-ZERO EVEN frequency

Constraints:
------------
- String length: 3 <= s.length <= 100
- Contains only lowercase English letters
- Guaranteed: At least one odd and one even frequency

Difficulty:
-----------
Easy
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   1. Count frequency of each character in the string.
   2. Extract all characters with odd frequency.
   3. Extract all characters with even frequency (non-zero).
   4. Try every pair (oddChar, evenChar) → compute diff.
   5. Track maximum diff.

   Why O(26^2)?
   ------------
   - At most 26 letters in lowercase English.
   - Odd set <= 26, Even set <= 26.
   - Checking all pairs = O(26*26) = O(676), trivial.

   =========================================================
   BRUTE FORCE CODE (COMMENTED)
   =========================================================
*/
/*
import java.util.*;

class SolutionBruteForce {
    public int maxDifference(String s) {
        int[] freq = new int[26];

        // Step 1: Count frequencies
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Step 2 & 3: collect odd and even freq chars
        List<Integer> oddFreq = new ArrayList<>();
        List<Integer> evenFreq = new ArrayList<>();

        for (int f : freq) {
            if (f > 0) {
                if (f % 2 == 1) oddFreq.add(f);
                else evenFreq.add(f);
            }
        }

        int maxDiff = Integer.MIN_VALUE;

        // Step 4: try all odd-even pairs
        for (int o : oddFreq) {
            for (int e : evenFreq) {
                maxDiff = Math.max(maxDiff, o - e);
            }
        }

        return maxDiff;
    }
}
*/

/*
------------------------------------------------------------
Step-by-step Complexity (Brute Force):
- Counting frequencies: O(n), n = string length.
- Collect odd/even: O(26).
- Check all pairs: O(26*26) = O(1) practically.
- Time = O(n).
- Space = O(26) = O(1).
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH
   =========================================================
   Logic:
   ------
   1. Count frequencies of all 26 chars.
   2. Find:
        - The maximum odd frequency (since we want freq[a1] as large as possible).
        - The minimum even frequency (since we want freq[a2] as small as possible, > 0).
   3. Answer = maxOdd - minEven.

   Why this works?
   ---------------
   - diff = odd - even.
   - To maximize diff → choose largest odd and smallest even.
   - No need to check all pairs.

   Tradeoffs:
   ----------
   - Brute force checks O(26*26).
   - Optimized just scans array once.
========================================================
*/

import java.util.*;

class Solution {
    public int maxDifference(String s) {
        int[] freq = new int[26];

        // Step 1: count frequency
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Step 2: track max odd and min even
        int maxOdd = Integer.MIN_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int f : freq) {
            if (f > 0) {
                if (f % 2 == 1) {
                    maxOdd = Math.max(maxOdd, f);
                } else {
                    minEven = Math.min(minEven, f);
                }
            }
        }

        // Step 3: return result
        return maxOdd - minEven;
    }
}

/*
=========================================================
TIME & SPACE COMPLEXITY
=========================================================
Brute Force:
------------
- Time: O(n + 26^2) ~ O(n).
- Space: O(26) ~ O(1).

Optimized:
----------
- Time: O(n + 26) ~ O(n).
- Space: O(26) ~ O(1).

=========================================================
INTERVIEW THINKING FLOW
=========================================================
1. Start brute force: generate odd/even freq sets, try all pairs.
2. Realize only the "largest odd" and "smallest even" matter.
3. Optimization: replace nested loops with two scans.
4. Data structure: fixed-size int[26], because alphabet is constant.
   Tradeoff: O(1) space vs maps, but faster & cleaner.
5. Final: O(n) time, O(1) space. Scales well even if n=10^5.
=========================================================
*/
