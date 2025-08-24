/*
Microsoft | Medium | Binary Tree | BFS Pattern
------------------------------------------------
Problem:
--------
Given the root of a binary tree, return the level order traversal 
of its nodes' values. (i.e., from left to right, level by level).

Pattern:
--------
- BFS (Breadth First Search)
- Use Queue for traversal

Difficulty:
-----------
Medium
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   - Use recursion (DFS) and track the "level" of each node.
   - Store nodes in a HashMap<level, List<Integer>>.
   - After traversal, convert map values into a result list.

   Step-by-step:
   -------------
   1. Define recursive DFS(root, level).
   2. If root is null → return.
   3. Add root.val to map[level].
   4. Call DFS(root.left, level+1), DFS(root.right, level+1).
   5. Finally, collect map entries into result.

   Complexity Building:
   --------------------
   - Each node visited once → O(n).
   - Storing in map costs O(1) each → total O(n).
   - But recursion stack may go deep → O(h) space (worst O(n)).

   Brute force uses DFS, less intuitive for "level-order",
   but works.

   =========================================================
   BRUTE FORCE CODE (DFS + Map)
   =========================================================
*/

// Definition for a binary tree node.
/*
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class SolutionBruteForce {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        dfs(root, 0, map);

        // Collect levels into result
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            result.add(map.get(i));
        }
        return result;
    }

    // DFS helper
    private void dfs(TreeNode node, int level, Map<Integer, List<Integer>> map) {
        if (node == null) return;

        // Add current node value to its level list
        map.computeIfAbsent(level, k -> new ArrayList<>()).add(node.val);

        // Recurse left and right
        dfs(node.left, level + 1, map);
        dfs(node.right, level + 1, map);
    }
}

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (Brute Force - DFS):
--------------------------------------------
Time = O(n) 
   - Each node is visited once.
Space = O(n) 
   - For storing result.
   - Plus recursion stack (O(h)), worst O(n) if skewed tree.

Why not best?
-------------
- DFS works but unnatural for "level-order".
- BFS queue directly models "level by level".
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH (BFS with Queue)
   =========================================================
   Logic:
   ------
   - Use a Queue for BFS traversal.
   - Push root into queue.
   - While queue not empty:
       * Process all nodes at current level (size = queue.size()).
       * Pop each node, add its value to level list.
       * Push its children into queue.
   - Add level list to result.

   Why Queue is Best?
   ------------------
   - BFS processes nodes in exact "level order".
   - Avoids recursion overhead.
   - Naturally fits problem statement.

   =========================================================
   OPTIMIZED CODE (BFS)
   =========================================================
*/

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result; // edge case: empty tree

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root); // start BFS with root

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // number of nodes in current level
            List<Integer> currentLevel = new ArrayList<>();

            // Process all nodes at this level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll(); // dequeue
                currentLevel.add(node.val);   // add value

                // Add children to queue
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            // Add this level's list to result
            result.add(currentLevel);
        }

        return result;
    }
}

/*
=========================================================
TIME & SPACE COMPLEXITY (Optimized BFS)
=========================================================
Time = O(n) 
   - Each node is processed once.
Space = O(n) 
   - Queue stores nodes level by level.
   - Result stores all nodes.

=========================================================
INTERVIEW THINKING FLOW
=========================================================
1. Brute force idea: DFS with level tracking.
   - Works but DFS is unnatural for level order.
2. Optimized idea: BFS queue matches the problem exactly.
   - Directly processes level by level.
3. Why Queue is best:
   - Ensures nodes are popped in order.
   - Avoids unnecessary recursion stack.
4. Tradeoffs:
   - DFS: shorter code, but less intuitive for level traversal.
   - BFS: more natural, best balance of clarity + efficiency.

Final Choice:
-------------
BFS with queue → O(n) time, O(n) space → most optimal.
=========================================================
*/
