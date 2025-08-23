/*
Microsoft | String + HashMap | Simulation Pattern | Easy

Problem:
--------
Roman numerals are represented by seven different symbols: 
I(1), V(5), X(10), L(50), C(100), D(500), M(1000).

Special subtraction rules:
- I before V or X → 4, 9
- X before L or C → 40, 90
- C before D or M → 400, 900

Given a roman numeral string s, convert it to an integer.

Examples:
---------
Input: "III"      → Output: 3
Input: "LVIII"    → Output: 58   (L=50, V=5, III=3)
Input: "MCMXCIV"  → Output: 1994 (M=1000, CM=900, XC=90, IV=4)

Constraints:
------------
- 1 <= s.length <= 15
- s is a valid roman numeral between [1, 3999]


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- Roman numerals follow subtraction rules in specific cases.
- Brute force: 
   1. Create a list of all possible roman numeral values (I, IV, V, IX, X, XL, ... M).
   2. Match substrings of input string against this list (from left to right).
   3. Add up values until string is fully parsed.
- This works but involves substring comparisons repeatedly.

Brute Force Code:
-----------------
public int romanToInt(String s) {
    Map<String, Integer> map = new HashMap<>();
    map.put("I", 1); map.put("IV", 4); map.put("V", 5); map.put("IX", 9);
    map.put("X", 10); map.put("XL", 40); map.put("L", 50); map.put("XC", 90);
    map.put("C", 100); map.put("CD", 400); map.put("D", 500); map.put("CM", 900);
    map.put("M", 1000);

    int i = 0, result = 0;
    while (i < s.length()) {
        if (i + 1 < s.length() && map.containsKey(s.substring(i, i+2))) {
            result += map.get(s.substring(i, i+2));
            i += 2;
        } else {
            result += map.get(s.substring(i, i+1));
            i++;
        }
    }
    return result;
}

Time Complexity:
---------------
- At each step we check 1 or 2 characters → O(n)
- But substring creation is costly (O(n) each).
- Overall worst-case → O(n^2).

Space Complexity:
----------------
- HashMap stores fixed mappings → O(1).
- Extra substrings in memory → O(n).
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force creates substrings repeatedly → inefficient.
2. Observation:
   - When a smaller value appears before a larger one, subtract it.
   - Otherwise, add normally.
   - Example: "IX" = 10 - 1 = 9.
3. We don’t need substrings; we just need to look at each character and the next one.
4. Data structure:
   - Use a **HashMap<Character, Integer>** to store values.
   - Traverse string once, compare current value vs next value.
   - This avoids extra substrings.
5. Why HashMap?
   - O(1) lookup for roman numeral values.
   - Cleaner than switch-case, easy to maintain.
6. Tradeoffs:
   - HashMap vs switch-case: both O(1).  
     HashMap is flexible (extendable if needed).  
     Switch-case is slightly faster in raw performance.  
   - For interviews → HashMap is clear and shows structured thinking.


--------------------------------------
OPTIMIZED SOLUTION (One-pass using HashMap)
--------------------------------------
*/

import java.util.*;

class Solution {
    public int romanToInt(String s) {
        // Step 1: Create map of roman values
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int result = 0;

        // Step 2: Traverse string
        for (int i = 0; i < s.length(); i++) {
            int value = map.get(s.charAt(i));

            // If next value is larger → subtract current
            if (i + 1 < s.length() && value < map.get(s.charAt(i + 1))) {
                result -= value;
            } 
            // Else → add current
            else {
                result += value;
            }
        }

        return result;
    }
}

/*
Time Complexity:
---------------
- Single traversal → O(n), where n = length of string.
- Each map lookup is O(1).
- Overall: O(n)

Space Complexity:
----------------
- HashMap of fixed 7 symbols → O(1).
- No extra data structures.
- Overall: O(1)
*/
