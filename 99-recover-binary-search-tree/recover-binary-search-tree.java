/*
Microsoft | Binary Search Tree | Inorder Traversal Pattern | Medium

Problem:
--------
Two nodes in a BST are swapped by mistake.
Recover the tree without changing its structure.

Examples:
---------
Input:  [1,3,null,null,2]
Output: [3,1,null,null,2]

Input:  [3,1,4,null,null,2]
Output: [2,1,4,null,null,3]

Constraints:
------------
- Nodes in range [2, 1000].
- -2^31 <= Node.val <= 2^31 - 1

Follow-up:
----------
Recover tree with O(1) space (instead of O(n)).
*/


/*
--------------------------------------
BRUTE FORCE SOLUTION
--------------------------------------

Logic:
------
1. Perform inorder traversal of BST.
   - Inorder of BST must be strictly increasing.
   - If nodes are swapped, this order breaks.
2. Store all values in a list.
3. Sort the list → gives the correct order.
4. Do inorder traversal again, replacing node values from sorted list.

Code:
------
public void recoverTreeBruteForce(TreeNode root) {
    List<Integer> inorder = new ArrayList<>();
    inorderTraversal(root, inorder);

    Collections.sort(inorder);

    int[] index = {0};
    replaceValues(root, inorder, index);
}

private void inorderTraversal(TreeNode node, List<Integer> inorder) {
    if (node == null) return;
    inorderTraversal(node.left, inorder);
    inorder.add(node.val);
    inorderTraversal(node.right, inorder);
}

private void replaceValues(TreeNode node, List<Integer> inorder, int[] index) {
    if (node == null) return;
    replaceValues(node.left, inorder, index);
    node.val = inorder[index[0]++];
    replaceValues(node.right, inorder, index);
}

Step-by-step Time Complexity:
-----------------------------
1. Inorder traversal to collect → O(n).
2. Sorting values → O(n log n).
3. Inorder traversal to replace → O(n).
4. Total = O(n log n).

Space Complexity:
-----------------
- Store inorder list O(n).
- Recursion stack O(h) (h = tree height).

Final:
------
- Time = O(n log n)
- Space = O(n)

Why not optimal?
----------------
- We sorted unnecessarily.
- Actually only two nodes are misplaced → no need full sort.
*/


/*
--------------------------------------
MOVING TO OPTIMIZATION
--------------------------------------

Observation:
------------
- Inorder traversal of BST should be strictly increasing.
- If two nodes are swapped, there will be **at most two violations**:
  Case 1: Adjacent nodes swapped → one violation.
  Case 2: Non-adjacent nodes swapped → two violations.

Optimized Idea:
---------------
1. Traverse inorder.
2. Track previous node.
3. Identify first and second nodes where violation occurs:
   - First violation: prev > curr → mark "first = prev".
   - Second violation: prev > curr again → mark "second = curr".
4. Swap first and second.

Why This Works:
---------------
- Only two misplaced nodes → fixing them restores BST.
- No need to store full list.
- Traversal can be done:
  - Recursive (O(n), O(h) stack).
  - Iterative with stack (O(n), O(h) stack).
  - Morris Traversal (O(n), O(1) space).

Tradeoffs:
----------
- Recursive/Iterative: easy but O(h) space.
- Morris Traversal: trickier but O(1) space → best.
*/


/*
--------------------------------------
OPTIMIZED SOLUTION (Morris Traversal, O(1) space)
--------------------------------------
*/

class Solution {
    TreeNode first = null, second = null, prev = null;

    public void recoverTree(TreeNode root) {
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left == null) {
                // Visit node
                detect(curr);
                curr = curr.right;
            } else {
                // Find predecessor
                TreeNode pre = curr.left;
                while (pre.right != null && pre.right != curr) {
                    pre = pre.right;
                }

                if (pre.right == null) {
                    // Create thread
                    pre.right = curr;
                    curr = curr.left;
                } else {
                    // Thread exists → revert it
                    pre.right = null;
                    detect(curr);
                    curr = curr.right;
                }
            }
        }

        // Swap the two wrong nodes
        if (first != null && second != null) {
            int temp = first.val;
            first.val = second.val;
            second.val = temp;
        }
    }

    // Detect violations in inorder sequence
    private void detect(TreeNode node) {
        if (prev != null && prev.val > node.val) {
            if (first == null) {
                first = prev;   // first violation
            }
            second = node;      // second violation (or update)
        }
        prev = node;
    }
}

/*
Time Complexity:
---------------
- Morris Traversal visits each node at most twice → O(n).

Space Complexity:
----------------
- O(1) (no stack, no recursion, only a few pointers).

--------------------------------------
Interview Tip:
--------------
1. Start brute force (inorder + sort, O(n log n)).
2. Improve: detect only 2 wrong nodes in inorder (O(n), O(h) space).
3. Optimize: Morris Traversal for O(1) space.
4. Explain that BST inorder must be strictly increasing → that’s the key invariant.
*/
