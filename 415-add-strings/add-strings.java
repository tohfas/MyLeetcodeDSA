/*
Microsoft | String Manipulation | Easy
--------------------------------------------------
Problem:
--------
Given two non-negative integers num1 and num2 as strings,
return their sum as a string.

Constraints:
- Cannot use built-in BigInteger or direct integer conversion.
- Must handle very large inputs (length up to 10^4).

Pattern:
--------
String Addition / Simulation of elementary school addition.

Difficulty:
-----------
Easy
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   1. Convert both strings into integers using built-in parsing (Integer.parseInt or Long.parseLong).
   2. Add them directly.
   3. Convert back to string.

   Why this is not allowed:
   ------------------------
   - Fails when numbers exceed range of int/long.
   - Problem explicitly disallows this approach.

   Still, let’s analyze complexity:
   --------------------------------
   - Parsing strings: O(n + m).
   - Addition: O(1).
   - Converting back to string: O(n).
   - Total: O(n + m).

   But correctness fails on large inputs (overflow).
--------------------------------------------------------
   BRUTE FORCE CODE (for learning only):
--------------------------------------------------------
   class SolutionBruteForce {
       public String addStrings(String num1, String num2) {
           int a = Integer.parseInt(num1);
           int b = Integer.parseInt(num2);
           int sum = a + b;
           return String.valueOf(sum);
       }
   }
--------------------------------------------------------
   Time Complexity (Brute Force):
   - O(n + m)
   Space Complexity:
   - O(1)
   Limitation: Overflows for big inputs.
========================================================
*/


/* =========================================================
   OPTIMIZED APPROACH (Digit-by-Digit Simulation)
   =========================================================
   Logic:
   ------
   1. Initialize two pointers:
      - i at end of num1
      - j at end of num2
   2. Initialize carry = 0.
   3. While i >= 0 OR j >= 0 OR carry != 0:
      - Extract digit from num1[i] (if available), else 0.
      - Extract digit from num2[j] (if available), else 0.
      - Compute sum = digit1 + digit2 + carry.
      - Append (sum % 10) to result.
      - Update carry = sum / 10.
   4. Reverse result at the end.

   Why this works best:
   ---------------------
   - No integer overflow.
   - Works for numbers of length up to 10^4.
   - Uses StringBuilder for efficient appending.

   Tradeoffs:
   ----------
   - Slightly more code compared to brute force.
   - But guaranteed correctness and scalability.
========================================================
*/

class Solution {
    public String addStrings(String num1, String num2) {
        // StringBuilder to store result in reverse order
        StringBuilder sb = new StringBuilder();

        int i = num1.length() - 1; // pointer for num1
        int j = num2.length() - 1; // pointer for num2
        int carry = 0;             // carry for addition

        // Loop until all digits and carry processed
        while (i >= 0 || j >= 0 || carry > 0) {
            int d1 = (i >= 0) ? num1.charAt(i) - '0' : 0; // digit from num1
            int d2 = (j >= 0) ? num2.charAt(j) - '0' : 0; // digit from num2

            int sum = d1 + d2 + carry; // add digits + carry

            sb.append(sum % 10); // append last digit
            carry = sum / 10;    // update carry

            i--; // move left
            j--;
        }

        return sb.reverse().toString(); // reverse final result
    }
}

/*
=========================================================
TIME & SPACE COMPLEXITY
=========================================================
Time Complexity:
- Each digit processed once → O(max(n, m)).

Space Complexity:
- StringBuilder stores result → O(max(n, m)).
- No extra data structures beyond this.
- Overall: O(max(n, m)).

=========================================================
INTERVIEW THINKING FLOW:
=========================================================
1. Brute force idea: parse → add → toString.
   - Quickly rejected due to overflow and constraints.

2. Optimized idea: simulate addition like pen & paper.
   - Process digits from right to left with carry.
   - Append results and reverse.

3. Why StringBuilder?
   - Efficient appending at end.
   - Reversal O(n) vs inserting at front O(n^2).

4. Tradeoff:
   - Brute force simpler but unsafe.
   - Simulation safe, linear time & space.
=========================================================
*/
