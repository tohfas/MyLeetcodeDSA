/*
Microsoft | Backtracking + DFS + Pruning | Medium
Pattern: Subset / Combination Generation (with uniqueness handling)

--------------------------------------------------------
PROBLEM:
--------------------------------------------------------
Given a list of candidate numbers (candidates) and a target, 
find all unique combinations where the candidate numbers sum to target.

- Each candidate can be used AT MOST once.
- The result must not contain duplicate combinations.
- Return list of lists.

--------------------------------------------------------
BRUTE FORCE (NOT OPTIMAL)
--------------------------------------------------------

Logic:
------
1. Generate *all subsets* of the candidates array. (2^n possibilities).
2. For each subset, check if its sum == target.
3. To ensure uniqueness, sort each subset and use a HashSet to store only unique ones.

Code (pseudo / commented, too slow for constraints):
--------------------------------------------------------
public List<List<Integer>> bruteForce(int[] candidates, int target) {
    int n = candidates.length;
    List<List<Integer>> result = new ArrayList<>();
    Set<List<Integer>> seen = new HashSet<>();

    // iterate over all subsets using bitmask
    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();
        int sum = 0;
        for (int j = 0; j < n; j++) {
            if ((mask & (1 << j)) != 0) {
                sum += candidates[j];
                subset.add(candidates[j]);
            }
        }
        if (sum == target) {
            Collections.sort(subset); // normalize for uniqueness
            if (!seen.contains(subset)) {
                seen.add(subset);
                result.add(subset);
            }
        }
    }
    return result;
}

Step-by-step Complexity:
------------------------
- Number of subsets = 2^n.
- For each subset: O(n) to compute sum + O(n log n) for sorting.
- Total = O(2^n * n log n).
- Space = O(2^n) for storing subsets.
- For n up to 100, this is absolutely infeasible.

--------------------------------------------------------
HOW TO THINK OPTIMIZED
--------------------------------------------------------
1. The brute force wasted time generating *all subsets* including irrelevant ones.
2. Instead, we can **explore only subsets that can potentially reach the target** using *backtracking*.
   - Sort candidates → helps skip duplicates.
   - Recursively build combinations.
   - Keep running sum; prune when it exceeds target.
3. Backtracking ensures we explore only valid paths.
4. To handle duplicates:
   - When looping at one recursion level, if current element == previous element → skip to avoid duplicate combinations.
5. This pruning makes solution exponential only on valid paths, not all 2^n subsets.

Why Backtracking + Sorting?
---------------------------
- Sorting + skipping duplicates ensures uniqueness (no HashSet needed).
- Backtracking prunes impossible sums early → huge optimization.
- Tradeoff:
   - HashSet method works but wastes time/memory.
   - Backtracking is best structured choice.

--------------------------------------------------------
OPTIMIZED SOLUTION
--------------------------------------------------------
*/

import java.util.*;

class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        
        // Step 1: Sort to help skip duplicates and prune
        Arrays.sort(candidates);
        
        // Step 2: Backtracking function
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        
        return result;
    }
    
    private void backtrack(int[] candidates, int remain, int start, List<Integer> path, List<List<Integer>> result) {
        // Base case: if remain == 0, valid combination found
        if (remain == 0) {
            result.add(new ArrayList<>(path)); // add copy
            return;
        }
        
        // Explore candidates from "start" onwards
        for (int i = start; i < candidates.length; i++) {
            // Skip duplicates at same recursion level
            if (i > start && candidates[i] == candidates[i - 1]) continue;
            
            // If current candidate > remain → no need to continue
            if (candidates[i] > remain) break;
            
            // Choose current candidate
            path.add(candidates[i]);
            
            // Recurse with updated remain, move index forward (i+1, since each can be used once)
            backtrack(candidates, remain - candidates[i], i + 1, path, result);
            
            // Backtrack (remove last choice)
            path.remove(path.size() - 1);
        }
    }
}

/*
--------------------------------------------------------
TIME & SPACE COMPLEXITY
--------------------------------------------------------

Brute Force:
------------
- Time = O(2^n * n log n), exponential and infeasible.
- Space = O(2^n).

Optimized (Backtracking with pruning):
--------------------------------------
- Time: 
   * Worst-case still exponential (combinatorial explosion),
     but heavily pruned.
   * Sorting = O(n log n).
   * Backtracking explores only valid subsets.
- Space: 
   * O(n) recursion depth.
   * O(#valid combinations * avg length) for output.

--------------------------------------------------------
INTERVIEW EXPLANATION FLOW:
--------------------------------------------------------
1. Start with brute force (generate all subsets).
2. Realize it’s infeasible because of exponential blowup.
3. Optimize with backtracking:
   - Sort array
   - Skip duplicates at same recursion depth
   - Prune paths when sum > target
4. This ensures only relevant combinations are explored.
5. Chosen data structure: recursion + path list.
   - Better than HashSet because we avoid duplicates upfront.

--------------------------------------------------------
*/
