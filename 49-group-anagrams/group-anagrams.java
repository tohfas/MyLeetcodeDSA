/*
Microsoft | String + Hashing | HashMap Pattern | Medium

Problem:
--------
Given an array of strings strs, group the anagrams together. 
You can return the answer in any order.

Examples:
---------
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

Input: strs = [""]
Output: [[""]]

Input: strs = ["a"]
Output: [["a"]]

Constraints:
------------
- 1 <= strs.length <= 10^4
- 0 <= strs[i].length <= 100
- strs[i] consists of lowercase English letters.


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- Anagrams are words that contain the same characters in any order.
- One brute force approach:
   1. For each string, generate all possible permutations.
   2. Check if any permutation matches other strings.
   3. Group them accordingly.
- This is very expensive because generating permutations is factorial time.

Better brute force approach:
- Sort each string’s characters alphabetically.
- Compare sorted strings: if they match, they are anagrams.
- Group together.

Brute Force Code:
-----------------
public List<List<String>> groupAnagrams(String[] strs) {
    boolean[] visited = new boolean[strs.length];
    List<List<String>> result = new ArrayList<>();

    for (int i = 0; i < strs.length; i++) {
        if (!visited[i]) {
            List<String> group = new ArrayList<>();
            group.add(strs[i]);
            visited[i] = true;

            // Compare with all other strings
            for (int j = i + 1; j < strs.length; j++) {
                if (!visited[j] && isAnagram(strs[i], strs[j])) {
                    group.add(strs[j]);
                    visited[j] = true;
                }
            }
            result.add(group);
        }
    }
    return result;
}

private boolean isAnagram(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    char[] c1 = s1.toCharArray();
    char[] c2 = s2.toCharArray();
    Arrays.sort(c1);
    Arrays.sort(c2);
    return Arrays.equals(c1, c2);
}

Time Complexity:
---------------
- Sorting each string: O(k log k) where k = avg length.
- Comparing pairs → O(n^2 * k log k)
- Too slow for n up to 10^4.

Space Complexity:
----------------
- Extra space for sorted arrays and visited[] → O(nk)
*/


/*
--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force tries to compare every string with others → O(n^2), too expensive.
2. Sorting-based brute force still repeats work.
3. What is unique about anagrams?
   - Anagrams have the same "character frequency".
   - For example: "eat" → {a:1, e:1, t:1}, "tea" → same map.
4. Idea:
   - Use a HashMap where:
       * Key = representation of character frequency (or sorted string).
       * Value = list of strings that share this key.
5. Why HashMap?
   - Fast lookups and groupings in O(1) avg time.
   - Avoids comparing every string with others.
6. Two common key approaches:
   - Sorted string key: "eat" → "aet"
   - Character count key: "eat" → "1#1#0#...#1..." (for 26 letters).
7. Both work, but sorted string key is simpler and clear for interviews.


--------------------------------------
OPTIMIZED SOLUTION (Using HashMap + Sorted Key)
--------------------------------------
*/

import java.util.*;

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // Map to group anagrams: sorted word -> list of words
        HashMap<String, List<String>> map = new HashMap<>();

        for (String word : strs) {
            // Sort characters of the word
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            // Add word to its group in the map
            map.putIfAbsent(sorted, new ArrayList<>());
            map.get(sorted).add(word);
        }

        // Return grouped anagrams
        return new ArrayList<>(map.values());
    }
}

/*
Time Complexity:
---------------
- Sorting each word: O(k log k), where k = length of word.
- Doing this for n words → O(n * k log k).

Space Complexity:
----------------
- HashMap stores all words grouped → O(n * k).
- Sorting uses O(k) per word.
- Overall Space Complexity = O(n * k).
*/
