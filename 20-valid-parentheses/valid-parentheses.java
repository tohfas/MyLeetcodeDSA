/*
Microsoft | Stack | String Parsing Pattern | Easy

Problem:
--------
Given a string s consisting of characters '(', ')', '{', '}', '[' and ']',
determine if the input string is valid.

Rules:
------
1. Open brackets must be closed by the same type.
2. Open brackets must be closed in the correct order.
3. Every closing bracket must have a corresponding opening.

Examples:
---------
Input: "()"       → true
Input: "()[]{}"   → true
Input: "(]"       → false
Input: "([])"     → true
Input: "([)]"     → false

Constraints:
------------
- 1 <= s.length <= 10^4
- s consists only of '()[]{}'
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

Logic:
------
- Keep replacing valid bracket pairs "()", "[]", "{}" from the string.
- Continue until no more replacements are possible.
- If the final string becomes empty → valid, else invalid.

Code:
------
public boolean isValidBruteForce(String s) {
    boolean changed = true;
    while (changed) {
        changed = false;
        if (s.contains("()")) {
            s = s.replace("()", "");
            changed = true;
        }
        if (s.contains("[]")) {
            s = s.replace("[]", "");
            changed = true;
        }
        if (s.contains("{}")) {
            s = s.replace("{}", "");
            changed = true;
        }
    }
    return s.isEmpty();
}

Step-by-step Time Complexity:
-----------------------------
1. Each replace operation scans string O(n).
2. Up to n/2 replacements possible.
3. Worst-case = O(n^2).

Space Complexity:
-----------------
- String operations create new strings repeatedly.
- Space = O(n) (since immutable strings in Java).

Final:
------
- Time = O(n^2) ❌ (too slow for 10^4 length).
- Space = O(n).
*/


/*
--------------------------------------
MOVING TO OPTIMIZATION
--------------------------------------

Observation:
------------
- A valid sequence behaves like a stack:
  - Every opening bracket must be matched with the latest unmatched opening.
- Example: "([])" → push '(' → push '[' → match ']' → match ')'.

Optimized Idea:
---------------
- Use a stack.
- For each char:
  - If opening → push.
  - If closing → check if stack top matches.
- At the end:
  - If stack empty → valid.
  - Else → invalid.

Why Stack?
----------
- Matches LIFO (Last In, First Out).
- Natural fit for parentheses matching.
- O(n) time and O(n) space.

Tradeoffs:
----------
- Brute force: easy but O(n^2).
- Stack: O(n) → optimal.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Stack Approach)
--------------------------------------
*/

import java.util.*;

class Solution {
    public boolean isValid(String s) {
        // Stack to hold opening brackets
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            // Push opening brackets
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                // Closing bracket must match top of stack
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }

        // At the end, stack must be empty
        return stack.isEmpty();
    }
}

/*
Time Complexity:
---------------
- O(n): each char processed once (push/pop O(1)).

Space Complexity:
----------------
- O(n): stack stores unmatched opening brackets.

--------------------------------------
Interview Tip:
--------------
1. Start brute force (replace pairs repeatedly → O(n^2)).
2. Transition: "This is inefficient. Notice parentheses validation works like LIFO → use stack."
3. Present stack solution O(n), O(n).
4. Emphasize: This is the industry-standard approach.
*/
