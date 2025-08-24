/*
Microsoft | Medium | Design + HashMap + Doubly Linked List | LRU Cache
----------------------------------------------------------------------------
Problem:
--------
Design a data structure implementing LRU Cache supporting:
1. get(key): return value if exists else -1
2. put(key, value): insert/update key; evict least recently used if over capacity.

Constraints:
- Must run in O(1) average time complexity.

Pattern:
--------
- This is a classic "LRU Cache" design problem.
- Combines HashMap (O(1) lookup) + Doubly Linked List (O(1) insert/remove).
- Used in Operating Systems, DB cache, Browsers etc.
----------------------------------------------------------------------------
*/


/* =========================================================
   BRUTE FORCE APPROACH
   =========================================================
   Logic:
   ------
   - Use an ArrayList to store keys in order of use.
   - get(key): O(n) search in list → if found move to end.
   - put(key, value):
       * If key exists: update value + move key to end.
       * If not exists:
           - If capacity full → remove first element (least recently used).
           - Insert new key at end.

   Step by Step:
   -------------
   1. Maintain ArrayList<Integer> order for recency.
   2. Maintain HashMap<Integer, Integer> for key-value storage.
   3. For get(key):
       - If exists: move key in order to last (most recent), return value.
       - Else return -1.
   4. For put(key, value):
       - If key exists: update + reorder in ArrayList.
       - If not: check capacity.
           * If full: evict order[0] (LRU).
           * Insert new key at end.

   Why slow?
   ---------
   - Removing an element from middle of ArrayList is O(n).
   - get/put become O(n) in worst case → not acceptable.

   =========================================================
   BRUTE FORCE CODE (O(n))
   =========================================================
*/
/*
import java.util.*;

class LRUCacheBruteForce {
    private int capacity;
    private Map<Integer, Integer> map;
    private List<Integer> order;

    public LRUCacheBruteForce(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.order = new ArrayList<>();
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        // Move key to most recent
        order.remove((Integer) key); // O(n) removal
        order.add(key);
        return map.get(key);
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            map.put(key, value);
            order.remove((Integer) key); // O(n)
            order.add(key);
        } else {
            if (map.size() == capacity) {
                int lru = order.remove(0); // remove first (LRU)
                map.remove(lru);
            }
            map.put(key, value);
            order.add(key);
        }
    }
}
*/

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY (Brute Force)
------------------------------------------------------------
get(key): O(n) due to list removal
put(key, value): O(n) due to list removal
Overall: O(n)
Space: O(capacity) for map + list
------------------------------------------------------------
Why not good?
-------------
Fails requirement: Must be O(1).
But brute force gives intuition that:
- Need O(1) order tracking (so ArrayList is bad).
- We need a data structure allowing quick removal/insert anywhere.
→ Doubly Linked List!
------------------------------------------------------------
*/


/* =========================================================
   OPTIMIZED APPROACH (HashMap + Doubly Linked List)
   =========================================================
   Key Insight:
   ------------
   - HashMap stores key → Node (value + DLL pointer).
   - Doubly Linked List keeps usage order (LRU at head, MRU at tail).
   - get(key):
       * If exists: move node to tail (most recent), return value.
       * Else return -1.
   - put(key, value):
       * If exists: update value + move node to tail.
       * Else: create node, add to tail.
           - If over capacity: remove from head (LRU) + remove from map.

   Why DLL?
   --------
   - O(1) removal from middle (unlike ArrayList).
   - O(1) insert at tail.
   - Combined with HashMap for direct access in O(1).

   =========================================================
   OPTIMIZED CODE (O(1) get/put)
   =========================================================
*/

import java.util.*;

class LRUCache {
    // Node structure for Doubly Linked List
    private class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }

    private int capacity;
    private Map<Integer, Node> map;  // key -> Node
    private Node head, tail;         // Dummy head & tail

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        // Initialize dummy nodes for easy edge handling
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);        // unlink node
        insertToTail(node);  // move to most recent
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // If key exists, update + move to tail
            Node node = map.get(key);
            node.value = value;
            remove(node);
            insertToTail(node);
        } else {
            if (map.size() == capacity) {
                // Evict LRU (head.next)
                Node lru = head.next;
                remove(lru);
                map.remove(lru.key);
            }
            // Insert new node
            Node node = new Node(key, value);
            map.put(key, node);
            insertToTail(node);
        }
    }

    // Helper: remove node from DLL
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Helper: insert node at tail (MRU position)
    private void insertToTail(Node node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }
}

/*
------------------------------------------------
TIME & SPACE COMPLEXITY
------------------------------------------------
get(key):
  - O(1) map lookup
  - O(1) DLL remove + insert
put(key, value):
  - O(1) map update or insertion
  - O(1) eviction if needed
Total: O(1) for both operations

Space:
  - O(capacity) for map + DLL nodes

------------------------------------------------
INTERVIEW THINKING FLOW
------------------------------------------------
1. Brute force: ArrayList for recency → O(n), fails.
2. Need O(1) recency updates → think Linked List.
3. But Linked List lookup = O(n), so combine with HashMap.
4. Map gives O(1) direct access; DLL gives O(1) recency updates.
5. This hybrid (HashMap + Doubly Linked List) is the standard
   LRU Cache solution used in real systems.
------------------------------------------------
*/
