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
- Repeatedly replace "()", "[]", "{}" with "" in the string.
- If string becomes empty → valid.
- Else → invalid.

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
1. Each replace → O(n).
2. At most n/2 replacements.
3. Worst case = O(n^2).
4. Space = O(n) (due to new string creation).

Final:
------
- Time = O(n^2) ❌
- Space = O(n)
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Deque<Character>)
--------------------------------------

Idea:
-----
- Use stack (Deque) to hold opening brackets.
- On closing bracket:
  - Check top of stack.
  - If mismatch → invalid.
- End: if stack empty → valid.

Why Stack?
----------
- LIFO matches parentheses pairing.
- O(n) time, O(n) space.

Code:
------
*/

import java.util.*;

class SolutionDeque {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c); // push opening
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                    (c == ']' && top != '[') ||
                    (c == '}' && top != '{')) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}

/*
Complexity:
-----------
- Time = O(n) → each char processed once, push/pop O(1).
- Space = O(n) → stack holds at most n chars.
*/


/*
--------------------------------------
FURTHER OPTIMIZED SOLUTION (char[] stack)
--------------------------------------

Idea:
-----
- Avoid Deque overhead and Character boxing.
- Use raw char[] as stack.
- Push/pop using array index → faster.

Code:
------
*/

class Solution {
    public boolean isValid(String s) {
        char[] stack = new char[s.length()];
        int top = -1; // stack pointer

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack[++top] = c; // push
            } else {
                if (top == -1) return false; // no opening
                char open = stack[top--];    // pop
                if ((c == ')' && open != '(') ||
                    (c == ']' && open != '[') ||
                    (c == '}' && open != '{')) {
                    return false;
                }
            }
        }

        return top == -1; // stack must be empty
    }
}

/*
Complexity:
-----------
- Time = O(n): single pass.
- Space = O(n): stack in worst case.
- Runs faster than Deque<Character> (avoids boxing & method overhead).

--------------------------------------
Interview Tip:
--------------
1. Start brute force (O(n^2)) → explain inefficiency.
2. Transition: "We need LIFO → Stack is natural fit."
3. Show Deque<Character> solution (clean for interviews).
4. Then mention optimization: using char[] stack improves Java runtime
   (fewer allocations, avoids boxing).
*/
