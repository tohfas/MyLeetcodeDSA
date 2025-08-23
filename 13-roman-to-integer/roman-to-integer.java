/*
Microsoft | String + HashMap/Array | Simulation Pattern | Easy

Problem:
--------
Roman numerals are represented by seven different symbols: 
I(1), V(5), X(10), L(50), C(100), D(500), M(1000).

Special subtraction rules:
- I before V or X → 4, 9
- X before L or C → 40, 90
- C before D or M → 400, 900

Given a Roman numeral string s, convert it to an integer.

Examples:
---------
Input: "III"      → Output: 3
Input: "LVIII"    → Output: 58   (L=50, V=5, III=3)
Input: "MCMXCIV"  → Output: 1994 (M=1000, CM=900, XC=90, IV=4)

Constraints:
------------
- 1 <= s.length <= 15
- s is a valid Roman numeral between [1, 3999]


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- Roman numerals can have both single characters and special 2-character pairs.
- Brute force idea:
   1. Store all valid numerals (I, IV, V, IX, X, XL, ... M).
   2. Traverse the string, check 2-character substrings first (like "IV", "CM").
   3. If match found → add its value and skip 2 chars.
   4. Else → add value of single char.
- Works but substring creation in Java is expensive.

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
- O(n) iterations, but substring creation O(n) each → worst-case O(n^2)

Space Complexity:
----------------
- HashMap fixed size → O(1)
- Substrings → O(n) extra
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Substring creation is costly in brute force.
2. Key observation:
   - Roman numeral rule: if a smaller value is before a larger → subtract it.
   - Otherwise → add it.
3. This means we don’t need substrings.
   Just compare current char with next char.
4. Data structure choice:
   - **HashMap<Character, Integer>**: O(1) lookup, clear to read.
   - But HashMap has hashing overhead.
   - For performance: **array[256] lookup** (ASCII) or **switch-case**.
5. Tradeoffs:
   - HashMap: More readable, slightly slower (~50% percentile).
   - Array lookup: Faster, ~90% percentile.
   - Switch-case: Fastest, minimal overhead, but less flexible.
6. For interviews:
   - Start with HashMap (clarity).
   - Mention that array/switch is more performant.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Array Lookup for Performance)
--------------------------------------
*/

class Solution {
    public int romanToInt(String s) {
        // Pre-fill values in ASCII array
        int[] values = new int[256];
        values['I'] = 1;
        values['V'] = 5;
        values['X'] = 10;
        values['L'] = 50;
        values['C'] = 100;
        values['D'] = 500;
        values['M'] = 1000;

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            int value = values[s.charAt(i)];

            // If next char is larger, subtract current
            if (i + 1 < s.length() && value < values[s.charAt(i + 1)]) {
                result -= value;
            } else {
                result += value;
            }
        }
        return result;
    }
}

/*
Time Complexity:
---------------
- Single pass through string → O(n)
- Constant-time array lookup → O(1)
- Overall: O(n)

Space Complexity:
----------------
- Fixed int[256] array → O(1)
- No extra structures.
*/
