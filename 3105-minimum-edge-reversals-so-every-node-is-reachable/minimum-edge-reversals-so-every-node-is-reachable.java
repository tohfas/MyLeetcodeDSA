/*
Microsoft | Graph | DFS + Re-rooting DP Pattern | Hard

Problem:
--------
We have a directed tree (n nodes, n-1 edges, if made undirected → a tree).
We can reverse edges. For each node, compute the minimum reversals required 
so that starting from this node, we can reach every other node.

Examples:
---------
Input: n = 4, edges = [[2,0],[2,1],[1,3]]
Output: [1,1,0,2]

Input: n = 3, edges = [[1,2],[2,0]]
Output: [2,0,1]

Constraints:
------------
- 2 <= n <= 10^5
- edges.length == n - 1
- Graph is a tree if undirected.


--------------------------------------
BRUTE FORCE SOLUTION (Explanation + Code in Comments)
--------------------------------------

How to Think Brute Force:
-------------------------
- For each node (as root):
   1. Run BFS/DFS.
   2. Whenever following an edge in wrong direction → count 1 reversal.
   3. Sum reversals required to reach all nodes.
- Do this for all n nodes.

Brute Force Code:
-----------------
public int[] minEdgeReversals(int n, int[][] edges) {
    List<int[]>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

    // Build adjacency: mark direction (0 = correct, 1 = needs reversal)
    for (int[] e : edges) {
        graph[e[0]].add(new int[]{e[1], 0}); // original direction
        graph[e[1]].add(new int[]{e[0], 1}); // reverse direction
    }

    int[] ans = new int[n];
    for (int start = 0; start < n; start++) {
        ans[start] = dfsCount(graph, start, -1);
    }
    return ans;
}

private int dfsCount(List<int[]>[] graph, int node, int parent) {
    int reversals = 0;
    for (int[] nei : graph[node]) {
        if (nei[0] != parent) {
            reversals += nei[1] + dfsCount(graph, nei[0], node);
        }
    }
    return reversals;
}

Time Complexity:
---------------
- DFS from each node → O(n) each.
- For n nodes → O(n^2), too slow (n = 10^5).

Space Complexity:
----------------
- O(n) adjacency list + recursion stack.


--------------------------------------
THINKING TOWARDS OPTIMIZATION
--------------------------------------

1. Brute force = O(n^2), impossible for n up to 10^5.
2. Key property: underlying graph is a **tree**.
   - A tree has n-1 edges, no cycles, only one path between nodes.
   - We can use **re-rooting technique**.
3. Idea:
   - Pick an arbitrary root (say node 0).
   - Count reversals needed if we root at 0.
   - Then, re-root at neighbors:
       * If edge u->v exists:
           - From u's perspective: fine.
           - From v’s perspective: this edge must be reversed → +1 for v.
       * If edge v->u exists:
           - From u’s perspective: must reverse.
           - From v’s perspective: fine → -1 for v.
   - Propagate counts across tree in one DFS.
4. Data structure choice:
   - **Adjacency list** → efficient for tree edges.
   - Store edge direction explicitly → so we know whether reversal is needed.
5. Why best?
   - Only two DFS traversals: one to compute base count, another to reroot.
   - Achieves O(n).
   - Alternatives (BFS per node) are O(n^2), infeasible.

--------------------------------------
OPTIMIZED SOLUTION (DFS + Re-rooting DP)
--------------------------------------
*/

import java.util.*;

class Solution {
    public int[] minEdgeReversals(int n, int[][] edges) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        // Build graph: mark direction (0 = correct, 1 = needs reversal if going that way)
        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], 0}); // forward edge
            graph[e[1]].add(new int[]{e[0], 1}); // reverse edge
        }

        int[] ans = new int[n];
        // Step 1: Count reversals if rooted at node 0
        ans[0] = dfsCount(graph, 0, -1);

        // Step 2: Re-root using dfsPropagate
        dfsPropagate(graph, 0, -1, ans);

        return ans;
    }

    // Count total reversals needed starting from node (root at 0)
    private int dfsCount(List<int[]>[] graph, int node, int parent) {
        int reversals = 0;
        for (int[] nei : graph[node]) {
            if (nei[0] != parent) {
                reversals += nei[1] + dfsCount(graph, nei[0], node);
            }
        }
        return reversals;
    }

    // Propagate reversal counts using re-rooting
    private void dfsPropagate(List<int[]>[] graph, int node, int parent, int[] ans) {
        for (int[] nei : graph[node]) {
            int neighbor = nei[0], cost = nei[1];
            if (neighbor != parent) {
                // If edge was forward (u->v), then from v's perspective one more reversal is needed
                // If edge was reverse (v->u), then from v's perspective one less reversal is needed
                if (cost == 0) {
                    ans[neighbor] = ans[node] + 1;
                } else {
                    ans[neighbor] = ans[node] - 1;
                }
                dfsPropagate(graph, neighbor, node, ans);
            }
        }
    }
}

/*
Time Complexity:
---------------
- DFS to count reversals → O(n)
- DFS to propagate results → O(n)
- Total: O(n)

Space Complexity:
----------------
- Adjacency list → O(n)
- Recursion stack → O(n)
- Overall: O(n)
*/
