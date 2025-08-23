/*
Microsoft | String | Dynamic Programming / Expand Around Center Pattern | Medium

Problem:
--------
Given a string s, return the longest palindromic substring in s.

Examples:
---------
Input: s = "babad" → Output: "bab" (or "aba")
Input: s = "cbbd"  → Output: "bb"

Constraints:
------------
1 <= s.length <= 1000
s consists of only digits and English letters.


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

Logic:
------
- Generate all possible substrings of s.
- For each substring, check if it is a palindrome.
- Keep track of the longest palindrome found so far.
- Very inefficient (O(n^3)).

Brute Force Code:
-----------------
public String longestPalindrome(String s) {
    int n = s.length();
    String longest = "";

    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            String sub = s.substring(i, j + 1);
            if (isPalindrome(sub) && sub.length() > longest.length()) {
                longest = sub;
            }
        }
    }
    return longest;
}

private boolean isPalindrome(String str) {
    int l = 0, r = str.length() - 1;
    while (l < r) {
        if (str.charAt(l) != str.charAt(r)) return false;
        l++;
        r--;
    }
    return true;
}

Time Complexity:
---------------
- Generate substrings: O(n^2)
- Check palindrome: O(n)
- Total → O(n^3)

Space Complexity:
----------------
- No extra space apart from temporary substrings → O(1) auxiliary
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Expand Around Center)
--------------------------------------

Logic:
------
- A palindrome mirrors around its center.
- There can be 2n - 1 centers (each char and between each pair of chars).
- Expand around each center while characters match.
- Keep track of the longest palindrome found.
- Much more efficient: O(n^2) time, O(1) space.
*/

class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // Case 1: Odd length palindrome (center at i)
            int len1 = expandFromCenter(s, i, i);

            // Case 2: Even length palindrome (center between i and i+1)
            int len2 = expandFromCenter(s, i, i + 1);

            int len = Math.max(len1, len2);

            // Update the longest palindrome boundaries
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    // Expands outward while left/right characters match
    private int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1; // length of palindrome
    }
}

/*
Time Complexity:
---------------
- For each character, expanding takes O(n) in the worst case.
- We do this for n characters → O(n^2).
- Overall Time Complexity = O(n^2)

Space Complexity:
----------------
- Only variables for indices.
- Overall Space Complexity = O(1).
*/
