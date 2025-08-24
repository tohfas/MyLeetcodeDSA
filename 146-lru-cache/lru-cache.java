/*
Microsoft | Medium | Design | LRU Cache
Pattern: HashMap + Doubly Linked List (Classic Design Problem)
------------------------------------------------------------
Problem:
--------
Implement LRUCache with O(1) average get/put.

Brute Force Idea:
-----------------
- Keep a list of keys in order of usage.
- On get/put: find key in list → move to "recently used end".
- Evict from front when full.

Why bad?
--------
- List search = O(n).
- Too slow for capacity up to 3000 and 2*10^5 calls.

Optimized Idea:
---------------
- HashMap + Doubly Linked List.
- HashMap → O(1) lookup of node.
- DLL → O(1) insert/remove/move operations.
- Maintain: 
   head <-> most recent, tail <-> least recent
- On get(key):
   * If exists → move node to head, return value.
   * Else return -1.
- On put(key, val):
   * If exists → update val, move to head.
   * Else:
       If size==capacity → evict tail.prev (least recent).
       Insert new node at head.
------------------------------------------------------------
*/


/* =========================================================
   BRUTE FORCE (for intuition, not efficient)
   =========================================================
   Logic:
   - Use ArrayList of pairs (key,val).
   - On get(key):
       * Linear search → if found, move pair to end (recent).
   - On put(key,val):
       * If exists → update and move to end.
       * Else insert at end.
       * If size > capacity → remove front (least recent).
   Time:
   - O(n) for search.
   Space:
   - O(capacity).
   =========================================================
*/
/*
class LRUCacheBruteForce {
    private int capacity;
    private List<int[]> cache; // stores {key,value}

    public LRUCacheBruteForce(int capacity) {
        this.capacity = capacity;
        this.cache = new ArrayList<>();
    }

    public int get(int key) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i)[0] == key) {
                int value = cache.get(i)[1];
                // move to end
                cache.remove(i);
                cache.add(new int[]{key, value});
                return value;
            }
        }
        return -1;
    }

    public void put(int key, int value) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i)[0] == key) {
                cache.remove(i);
                cache.add(new int[]{key, value});
                return;
            }
        }
        if (cache.size() == capacity) {
            cache.remove(0); // evict LRU
        }
        cache.add(new int[]{key, value});
    }
}
*/


/* =========================================================
   OPTIMIZED SOLUTION (HashMap + Doubly Linked List)
   =========================================================
*/

import java.util.*;

class LRUCache {
    // Node structure for DLL
    class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) { key = k; value = v; }
    }

    private int capacity;
    private Map<Integer, Node> map; // key → node
    private Node head, tail; // dummy nodes for DLL

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        // Create dummy head & tail
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        // move node to head (most recent)
        remove(node);
        insertToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // Update value & move to head
            Node node = map.get(key);
            node.value = value;
            remove(node);
            insertToHead(node);
        } else {
            if (map.size() == capacity) {
                // Evict LRU: tail.prev
                Node lru = tail.prev;
                remove(lru);
                map.remove(lru.key);
            }
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertToHead(newNode);
        }
    }

    // Remove node from DLL
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Insert node right after head
    private void insertToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}

/*
------------------------------------------------------------
TIME & SPACE COMPLEXITY
------------------------------------------------------------
Brute Force:
- get: O(n)
- put: O(n)
- Space: O(capacity)

Optimized (HashMap + DLL):
- get: O(1)
- put: O(1)
- Space: O(capacity) for HashMap + DLL

------------------------------------------------------------
INTERVIEW THINKING
------------------------------------------------------------
1. Start brute force → see inefficiency (O(n)).
2. Need O(1) lookup → HashMap.
3. Need O(1) eviction + move-to-front → Doubly Linked List.
4. Combine both → O(1) get/put.

Tradeoff:
---------
- Slightly more complex (extra pointers).
- But guarantees O(1).
------------------------------------------------------------
*/


/* =========================================================
   ALTERNATIVE SHORTCUT (LinkedHashMap)
   =========================================================
   - Java’s LinkedHashMap already keeps access order if enabled.
   - Override removeEldestEntry() to auto-evict.
   - Cleaner code, often faster in Java benchmarks.
*/
/*
class LRUCacheLinkedHashMap extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCacheLinkedHashMap(int capacity) {
        super(capacity, 0.75f, true); // true = access order
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
*/