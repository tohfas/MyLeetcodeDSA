/*
Microsoft | String | Sliding Window Pattern | Medium

Problem:
--------
Given a string s, find the length of the longest substring without duplicate characters.

Examples:
---------
Input: s = "abcabcbb"   → Output: 3 ("abc")
Input: s = "bbbbb"      → Output: 1 ("b")
Input: s = "pwwkew"     → Output: 3 ("wke")

Constraints:
------------
0 <= s.length <= 5 * 10^4
s consists of English letters, digits, symbols, and spaces.


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

Logic:
------
- Generate all possible substrings.
- For each substring, check if it contains all unique characters.
- Track the maximum length among valid substrings.
- Very inefficient for large inputs.

Brute Force Code:
-----------------
public int lengthOfLongestSubstring(String s) {
    int n = s.length();
    int maxLength = 0;

    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            // Check substring s[i..j]
            if (allUnique(s, i, j)) {
                maxLength = Math.max(maxLength, j - i + 1);
            }
        }
    }
    return maxLength;
}

private boolean allUnique(String s, int start, int end) {
    Set<Character> set = new HashSet<>();
    for (int k = start; k <= end; k++) {
        if (set.contains(s.charAt(k))) return false;
        set.add(s.charAt(k));
    }
    return true;
}

Time Complexity:
---------------
- Generating substrings: O(n^2)
- Checking uniqueness: O(n)
- Total → O(n^3)

Space Complexity:
----------------
- Uses a HashSet for checking uniqueness → O(min(n, charset_size))
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Using Sliding Window + HashMap)
--------------------------------------

Logic:
------
- Maintain a sliding window using two pointers (left, right).
- Use a HashMap to store the last index of each character.
- If a character is repeated, move `left` pointer just after the last occurrence.
- At each step, calculate window length = right - left + 1 and update max length.
- This ensures we never revisit characters unnecessarily.

*/

import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        // HashMap stores character -> last index seen
        HashMap<Character, Integer> map = new HashMap<>();
        int left = 0, maxLength = 0;

        // Expand window with 'right' pointer
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If character seen before and inside current window
            if (map.containsKey(c) && map.get(c) >= left) {
                // Move left pointer just after its last occurrence
                left = map.get(c) + 1;
            }

            // Store/update the last index of this character
            map.put(c, right);

            // Update maxLength
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}

/*
Time Complexity:
---------------
- Each character visited at most twice (right expands, left moves) → O(n)
- HashMap operations are O(1) average.
- Overall Time Complexity = O(n)

Space Complexity:
----------------
- HashMap stores at most min(n, charset_size) characters.
- For ASCII → O(128), for Unicode → O(n)
- Overall Space Complexity = O(min(n, charset_size))
*/
