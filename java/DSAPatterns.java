import java.util.*;

/**
 * 16 Essential DSA Patterns for Coding Interviews - Java Implementation
 * Each pattern includes minimal code with optimal time complexity examples
 */
public class DSAPatterns {
    
    // 1. Sliding Window - max sum of subarray size k
    static int maxSumK(int[] a, int k) {
        int sum = 0, best = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            if (i >= k) sum -= a[i - k];
            if (i >= k - 1) best = Math.max(best, sum);
        }
        return best;
    }
    
    // 2. Two Pointers - remove duplicates from sorted array
    static int dedup(int[] a) {
        if (a.length == 0) return 0;
        int w = 1;
        for (int r = 1; r < a.length; r++) 
            if (a[r] != a[w - 1]) a[w++] = a[r];
        return w;
    }
    
    // 3. Monotonic Stack - next greater element
    static int[] nextGreater(int[] a) {
        int n = a.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && a[i] > a[st.peek()]) 
                ans[st.pop()] = a[i];
            st.push(i);
        }
        return ans;
    }
    
    // 4. Heap (Top-K) - k largest elements
    static List<Integer> topK(int[] a, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int x : a) {
            pq.offer(x);
            if (pq.size() > k) pq.poll();
        }
        return new ArrayList<>(pq);
    }
    
    // 5. Binary Search - lower bound (first index >= target)
    static int lowerBound(int[] a, int t) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (a[m] < t) l = m + 1;
            else r = m;
        }
        return l;
    }
    
    // 6. Backtracking - all subsets
    static List<List<Integer>> subsets(int[] nums) {
        var res = new ArrayList<List<Integer>>();
        backtrack(0, new ArrayList<>(), nums, res);
        return res;
    }
    
    static void backtrack(int i, List<Integer> cur, int[] a, List<List<Integer>> res) {
        if (i == a.length) {
            res.add(new ArrayList<>(cur));
            return;
        }
        backtrack(i + 1, cur, a, res);
        cur.add(a[i]);
        backtrack(i + 1, cur, a, res);
        cur.remove(cur.size() - 1);
    }
    
    // 7. BFS - shortest path in 0/1 grid
    static int shortest(int[][] g) {
        int n = g.length, m = g[0].length;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] vis = new boolean[n][m];
        q.add(new int[]{0, 0, 0});
        vis[0][0] = true;
        int[][] d = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        
        while (!q.isEmpty()) {
            var cur = q.poll();
            int x = cur[0], y = cur[1], steps = cur[2];
            if (x == n - 1 && y == m - 1) return steps;
            
            for (var v : d) {
                int nx = x + v[0], ny = y + v[1];
                if (0 <= nx && nx < n && 0 <= ny && ny < m && 
                    g[nx][ny] == 0 && !vis[nx][ny]) {
                    vis[nx][ny] = true;
                    q.add(new int[]{nx, ny, steps + 1});
                }
            }
        }
        return -1;
    }
    
    // 8. Dynamic Programming - coin change
    static int coinChange(int[] coins, int amt) {
        int INF = 1_000_000_000;
        int[] dp = new int[amt + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        
        for (int c : coins) {
            for (int x = c; x <= amt; x++) {
                dp[x] = Math.min(dp[x], dp[x - c] + 1);
            }
        }
        return dp[amt] == INF ? -1 : dp[amt];
    }
    
    // 9. Fast/Slow Pointers - cycle detection
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }
    
    static ListNode cycleStart(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (fast == null || fast.next == null) return null;
        
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    
    // 10. In-place Linked List Reverse
    static ListNode reverse(ListNode head) {
        ListNode prev = null, cur = head;
        while (cur != null) {
            ListNode nxt = cur.next;
            cur.next = prev;
            prev = cur;
            cur = nxt;
        }
        return prev;
    }
    
    // 11. Intervals - merge overlapping
    static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> out = new ArrayList<>();
        
        for (var x : intervals) {
            if (out.isEmpty() || out.get(out.size() - 1)[1] < x[0]) {
                out.add(x.clone());
            } else {
                out.get(out.size() - 1)[1] = Math.max(out.get(out.size() - 1)[1], x[1]);
            }
        }
        return out.toArray(new int[0][]);
    }
    
    // 12. Prefix Sum + HashMap - subarray sum equals k
    static int subarraySum(int[] nums, int k) {
        var map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        int prefixSum = 0, count = 0;
        
        for (int x : nums) {
            prefixSum += x;
            count += map.getOrDefault(prefixSum - k, 0);
            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }
        return count;
    }
    
    // 13. Binary Search on Answer - split array minimize largest sum
    static int splitArray(int[] nums, int m) {
        int lo = 0, hi = 0;
        for (int x : nums) {
            lo = Math.max(lo, x);
            hi += x;
        }
        
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            int need = 1, cur = 0;
            for (int x : nums) {
                if (cur + x > mid) {
                    need++;
                    cur = 0;
                }
                cur += x;
            }
            if (need <= m) hi = mid;
            else lo = mid + 1;
        }
        return lo;
    }
    
    // 14. Monotonic Deque - sliding window maximum
    static int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> dq = new ArrayDeque<>();
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        
        for (int i = 0; i < n; i++) {
            while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) ans[i - k + 1] = nums[dq.peekFirst()];
        }
        return ans;
    }
    
    // 15. Topological Sort - course schedule
    static boolean canFinish(int numCourses, int[][] prerequisites) {
        var graph = new ArrayList<List<Integer>>();
        for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
        
        int[] indegree = new int[numCourses];
        for (var p : prerequisites) {
            graph.get(p[1]).add(p[0]);
            indegree[p[0]]++;
        }
        
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.add(i);
        }
        
        int completed = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            completed++;
            for (int next : graph.get(course)) {
                if (--indegree[next] == 0) queue.add(next);
            }
        }
        return completed == numCourses;
    }
    
    // 16. Union-Find - connected components
    static class UnionFind {
        int[] parent, rank;
        int components;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        
        boolean union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return false;
            if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
            parent[py] = px;
            if (rank[px] == rank[py]) rank[px]++;
            components--;
            return true;
        }
    }
    
    static int countComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        for (var e : edges) uf.union(e[0], e[1]);
        return uf.components;
    }
    
    public static void main(String[] args) {
        // Test examples
        System.out.println("=== DSA Patterns Demo ===");
        
        // 1. Sliding Window
        System.out.println("1. Max sum k=3: " + maxSumK(new int[]{1,2,3,4,5}, 3)); // 12
        
        // 2. Two Pointers  
        int[] arr = {1,1,2,2,3};
        System.out.println("2. Dedup length: " + dedup(arr)); // 3
        
        // 3. Monotonic Stack
        System.out.println("3. Next greater: " + Arrays.toString(nextGreater(new int[]{2,1,3}))); // [3,-1,-1]
        
        // 4. Heap
        System.out.println("4. Top 2: " + topK(new int[]{3,1,4,1,5}, 2)); // [3,4] or [4,5]
        
        // 5. Binary Search
        System.out.println("5. Lower bound of 3: " + lowerBound(new int[]{1,2,3,3,4}, 3)); // 2
        
        // 16. Union-Find
        System.out.println("16. Connected components: " + countComponents(5, new int[][]{{0,1},{1,2},{3,4}})); // 2
        
        System.out.println("All patterns implemented successfully!");
    }
}