/*
Microsoft | Medium | Array | Greedy + Two Pointers Pattern
------------------------------------------------------------
Problem:
--------
We are given an array nums[].
We must rearrange nums into its next lexicographically greater permutation.
If not possible (already last permutation), rearrange to smallest (ascending).

Constraints:
- Must be in-place
- Must use constant extra memory

Difficulty: Medium
*/


/* =========================================================
   BRUTE FORCE APPROACH (for intuition)
   =========================================================
   Logic:
   ------
   - Generate all permutations of nums[].
   - Sort them in lexicographical order.
   - Find current permutation’s index.
   - Next permutation = index+1 (if exists), else first permutation.

   Why it's bad:
   -------------
   - Generating all permutations = O(n!).
   - Sorting them = O(n! * n log(n!)).
   - For n up to 100 → infeasible.

   Step-by-step complexity reasoning:
   ----------------------------------
   - Number of permutations = n!.
   - Each permutation creation costs O(n).
   - So generation alone = O(n * n!).
   - Sorting permutations adds more.
   - Thus total ~ O(n! * n), exponential.

   Still useful to understand: "next permutation" is essentially
   finding the next lexicographic arrangement among all permutations.

   =========================================================
   BRUTE FORCE CODE (backtracking + lexicographic search)
   =========================================================
*/
/*
class SolutionBruteForce {
    public void nextPermutation(int[] nums) {
        // Generate all permutations
        List<int[]> all = new ArrayList<>();
        permute(nums, 0, all);

        // Convert to list of lists for comparison
        List<List<Integer>> perms = new ArrayList<>();
        for (int[] arr : all) {
            List<Integer> l = new ArrayList<>();
            for (int val : arr) l.add(val);
            perms.add(l);
        }

        // Sort lexicographically
        perms.sort((a, b) -> {
            for (int i = 0; i < a.size(); i++) {
                if (!a.get(i).equals(b.get(i))) return a.get(i) - b.get(i);
            }
            return 0;
        });

        // Find current permutation
        List<Integer> cur = new ArrayList<>();
        for (int val : nums) cur.add(val);

        int idx = -1;
        for (int i = 0; i < perms.size(); i++) {
            if (perms.get(i).equals(cur)) {
                idx = i;
                break;
            }
        }

        // Next = idx+1 else wrap to first
        List<Integer> next = (idx == perms.size()-1) ? perms.get(0) : perms.get(idx+1);
        for (int i = 0; i < nums.length; i++) nums[i] = next.get(i);
    }

    private void permute(int[] nums, int start, List<int[]> result) {
        if (start == nums.length) {
            result.add(nums.clone());
            return;
        }
        for (int i = start; i < nums.length; i++) {
            swap(nums, start, i);
            permute(nums, start+1, result);
            swap(nums, start, i); // backtrack
        }
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
*/

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (Brute Force)
------------------------------------------------------------
Time = O(n * n!) for generating permutations.
Space = O(n * n!) to store them.
→ Impossible for n > 9 or 10.

This shows brute force is impractical, but it clarifies the problem structure.
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH (Greedy + Two Pointers)
   =========================================================
   Key Insight:
   ------------
   - Next permutation is just "next lexicographic arrangement".
   - Steps:
       1. Traverse from right → find first index i such that nums[i] < nums[i+1].
          (this is the "pivot" where increase can happen).
       2. If no such i → array is descending → reverse to ascending → done.
       3. Else, find smallest element > nums[i] to the right (nums[j]).
       4. Swap nums[i] and nums[j].
       5. Reverse suffix from i+1 → end to get next smallest order.

   Example:
   --------
   nums = [1,2,3]
   - pivot = 1 (nums[1]=2, since 2<3)
   - find j=2 (nums[2]=3 > 2)
   - swap → [1,3,2]
   - reverse suffix (already sorted).
   Output = [1,3,2]

   =========================================================
   OPTIMIZED CODE
   =========================================================
*/

class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;

        // Step 1: Find pivot index i
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Step 2: Find rightmost element > nums[i]
            int j = n - 1;
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }

            // Step 3: Swap
            swap(nums, i, j);
        }

        // Step 4: Reverse suffix (i+1..end)
        reverse(nums, i + 1, n - 1);
    }

    // Utility swap
    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    // Utility reverse
    private void reverse(int[] nums, int l, int r) {
        while (l < r) {
            swap(nums, l, r);
            l++;
            r--;
        }
    }
}

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (Optimized)
------------------------------------------------------------
Time:
- Step 1: O(n) scan from right.
- Step 2: O(n) scan for swap partner.
- Step 3: O(n) reverse suffix.
Total = O(n).

Space:
- O(1), in-place operations.

------------------------------------------------------------
INTERVIEW THINKING FLOW
------------------------------------------------------------
1. Brute force = try all permutations → O(n!).
   → Realize infeasible.
2. Think lexicographic order:
   * When do permutations increase?
   * Rightmost "increasing pair" pivot idea.
3. To get *next* permutation:
   * Increase pivot minimally (swap with next larger element).
   * Reverse suffix to ensure smallest arrangement.

Data Structure Choice:
----------------------
- No extra DS needed → in-place swap & reverse.
- Best tradeoff: O(n) time, O(1) space.

This is the optimal solution.
------------------------------------------------------------
*/
