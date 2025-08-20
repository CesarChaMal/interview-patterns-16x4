/**
 * 15 Essential DSA Patterns for Coding Interviews - TypeScript Implementation
 * Modern TypeScript with strong typing and clean interfaces
 */

interface ListNode {
    val: number;
    next: ListNode | null;
}

class DSAPatterns {
    
    // 1. Sliding Window - max sum of subarray size k
    static maxSumK(nums: number[], k: number): number {
        let windowSum = 0;
        let maxSum = -Infinity;
        
        for (let i = 0; i < nums.length; i++) {
            windowSum += nums[i];
            if (i >= k) windowSum -= nums[i - k];
            if (i >= k - 1) maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    // 2. Two Pointers - remove duplicates from sorted array
    static removeDuplicates(nums: number[]): number {
        if (nums.length === 0) return 0;
        
        let write = 1;
        for (let read = 1; read < nums.length; read++) {
            if (nums[read] !== nums[write - 1]) {
                nums[write] = nums[read];
                write++;
            }
        }
        
        return write;
    }
    
    // 3. Monotonic Stack - next greater element
    static nextGreaterElement(nums: number[]): number[] {
        const n = nums.length;
        const result = new Array(n).fill(-1);
        const stack: number[] = [];
        
        for (let i = 0; i < n; i++) {
            while (stack.length && nums[i] > nums[stack[stack.length - 1]]) {
                result[stack.pop()!] = nums[i];
            }
            stack.push(i);
        }
        
        return result;
    }
    
    // 4. Heap (Top-K) - k largest elements (using array-based min heap)
    static topKElements(nums: number[], k: number): number[] {
        const heap: number[] = [];
        
        const heapifyUp = (idx: number) => {
            while (idx > 0) {
                const parent = Math.floor((idx - 1) / 2);
                if (heap[parent] <= heap[idx]) break;
                [heap[parent], heap[idx]] = [heap[idx], heap[parent]];
                idx = parent;
            }
        };
        
        const heapifyDown = (idx: number) => {
            while (true) {
                let smallest = idx;
                const left = 2 * idx + 1;
                const right = 2 * idx + 2;
                
                if (left < heap.length && heap[left] < heap[smallest]) smallest = left;
                if (right < heap.length && heap[right] < heap[smallest]) smallest = right;
                
                if (smallest === idx) break;
                [heap[idx], heap[smallest]] = [heap[smallest], heap[idx]];
                idx = smallest;
            }
        };
        
        for (const num of nums) {
            if (heap.length < k) {
                heap.push(num);
                heapifyUp(heap.length - 1);
            } else if (num > heap[0]) {
                heap[0] = num;
                heapifyDown(0);
            }
        }
        
        return heap;
    }
    
    // 5. Binary Search - lower bound
    static lowerBound(nums: number[], target: number): number {
        let left = 0, right = nums.length;
        
        while (left < right) {
            const mid = Math.floor((left + right) / 2);
            if (nums[mid] < target) left = mid + 1;
            else right = mid;
        }
        
        return left;
    }
    
    // 6. Backtracking - all subsets
    static subsets(nums: number[]): number[][] {
        const result: number[][] = [];
        const current: number[] = [];
        
        const backtrack = (index: number): void => {
            if (index === nums.length) {
                result.push([...current]);
                return;
            }
            
            // Don't include current element
            backtrack(index + 1);
            
            // Include current element
            current.push(nums[index]);
            backtrack(index + 1);
            current.pop();
        };
        
        backtrack(0);
        return result;
    }
    
    // 7. BFS - shortest path in 0/1 grid
    static shortestPathGrid(grid: number[][]): number {
        if (!grid.length || grid[0][0] === 1) return -1;
        
        const rows = grid.length, cols = grid[0].length;
        const queue: [number, number, number][] = [[0, 0, 0]];
        const visited = new Set<string>();
        visited.add('0,0');
        const directions = [[1, 0], [-1, 0], [0, 1], [0, -1]];
        
        while (queue.length) {
            const [row, col, dist] = queue.shift()!;
            
            if (row === rows - 1 && col === cols - 1) return dist;
            
            for (const [dr, dc] of directions) {
                const nr = row + dr, nc = col + dc;
                const key = `${nr},${nc}`;
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && 
                    grid[nr][nc] === 0 && !visited.has(key)) {
                    visited.add(key);
                    queue.push([nr, nc, dist + 1]);
                }
            }
        }
        
        return -1;
    }
    
    // 8. Dynamic Programming - coin change
    static coinChange(coins: number[], amount: number): number {
        const dp = new Array(amount + 1).fill(Infinity);
        dp[0] = 0;
        
        for (const coin of coins) {
            for (let x = coin; x <= amount; x++) {
                dp[x] = Math.min(dp[x], dp[x - coin] + 1);
            }
        }
        
        return dp[amount] === Infinity ? -1 : dp[amount];
    }
    
    // 9. Fast/Slow Pointers - cycle detection
    static detectCycleStart(head: ListNode | null): ListNode | null {
        if (!head) return null;
        
        let slow: ListNode | null = head;
        let fast: ListNode | null = head;
        
        // Find intersection
        while (fast && fast.next) {
            slow = slow!.next;
            fast = fast.next.next;
            if (slow === fast) break;
        }
        
        if (!fast || !fast.next) return null;
        
        // Find cycle start
        slow = head;
        while (slow !== fast) {
            slow = slow!.next;
            fast = fast!.next;
        }
        
        return slow;
    }
    
    // 10. In-place Linked List Reverse
    static reverseLinkedList(head: ListNode | null): ListNode | null {
        let prev: ListNode | null = null;
        let current = head;
        
        while (current) {
            const next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
    
    // 11. Intervals - merge overlapping
    static mergeIntervals(intervals: number[][]): number[][] {
        if (!intervals.length) return [];
        
        intervals.sort((a, b) => a[0] - b[0]);
        const merged = [intervals[0]];
        
        for (let i = 1; i < intervals.length; i++) {
            const current = intervals[i];
            const last = merged[merged.length - 1];
            
            if (last[1] >= current[0]) {
                last[1] = Math.max(last[1], current[1]);
            } else {
                merged.push(current);
            }
        }
        
        return merged;
    }
    
    // 12. Prefix Sum + HashMap - subarray sum equals k
    static subarraySum(nums: number[], k: number): number {
        const sumCount = new Map<number, number>();
        sumCount.set(0, 1);
        let prefixSum = 0;
        let count = 0;
        
        for (const num of nums) {
            prefixSum += num;
            count += sumCount.get(prefixSum - k) || 0;
            sumCount.set(prefixSum, (sumCount.get(prefixSum) || 0) + 1);
        }
        
        return count;
    }
    
    // 13. Binary Search on Answer - split array minimize largest sum
    static splitArray(nums: number[], m: number): number {
        const canSplit = (maxSum: number): boolean => {
            let splits = 1;
            let currentSum = 0;
            
            for (const num of nums) {
                if (currentSum + num > maxSum) {
                    splits++;
                    currentSum = 0;
                }
                currentSum += num;
            }
            
            return splits <= m;
        };
        
        let left = Math.max(...nums);
        let right = nums.reduce((sum, num) => sum + num, 0);
        
        while (left < right) {
            const mid = Math.floor((left + right) / 2);
            if (canSplit(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    // 14. Monotonic Deque - sliding window maximum
    static maxSlidingWindow(nums: number[], k: number): number[] {
        const deque: number[] = []; // stores indices
        const result: number[] = [];
        
        for (let i = 0; i < nums.length; i++) {
            // Remove indices outside window
            while (deque.length && deque[0] <= i - k) {
                deque.shift();
            }
            
            // Remove smaller elements from back
            while (deque.length && nums[deque[deque.length - 1]] <= nums[i]) {
                deque.pop();
            }
            
            deque.push(i);
            
            // Add to result when window is full
            if (i >= k - 1) {
                result.push(nums[deque[0]]);
            }
        }
        
        return result;
    }
    
    // 15. Topological Sort - course schedule
    static canFinish(numCourses: number, prerequisites: number[][]): boolean {
        const graph: number[][] = Array.from({ length: numCourses }, () => []);
        const indegree = new Array(numCourses).fill(0);
        
        // Build graph and indegree array
        for (const [course, prereq] of prerequisites) {
            graph[prereq].push(course);
            indegree[course]++;
        }
        
        // Start with courses having no prerequisites
        const queue: number[] = [];
        for (let i = 0; i < numCourses; i++) {
            if (indegree[i] === 0) queue.push(i);
        }
        
        let completed = 0;
        while (queue.length) {
            const course = queue.shift()!;
            completed++;
            
            for (const nextCourse of graph[course]) {
                indegree[nextCourse]--;
                if (indegree[nextCourse] === 0) {
                    queue.push(nextCourse);
                }
            }
        }
        
        return completed === numCourses;
    }
    
    // 16. Union-Find - connected components
    static class UnionFind {
        private parent: number[];
        private rank: number[];
        public components: number;
        
        constructor(n: number) {
            this.parent = Array.from({ length: n }, (_, i) => i);
            this.rank = new Array(n).fill(0);
            this.components = n;
        }
        
        find(x: number): number {
            if (this.parent[x] !== x) {
                this.parent[x] = this.find(this.parent[x]);
            }
            return this.parent[x];
        }
        
        union(x: number, y: number): boolean {
            const px = this.find(x);
            const py = this.find(y);
            
            if (px === py) return false;
            
            if (this.rank[px] < this.rank[py]) {
                this.parent[px] = py;
            } else if (this.rank[px] > this.rank[py]) {
                this.parent[py] = px;
            } else {
                this.parent[py] = px;
                this.rank[px]++;
            }
            
            this.components--;
            return true;
        }
    }
    
    static countComponents(n: number, edges: number[][]): number {
        const uf = new DSAPatterns.UnionFind(n);
        for (const [a, b] of edges) {
            uf.union(a, b);
        }
        return uf.components;
    }
}

// Demo function
function main(): void {
    console.log("=== DSA Patterns Demo (TypeScript) ===");
    
    // 1. Sliding Window
    console.log(`1. Max sum k=3: ${DSAPatterns.maxSumK([1,2,3,4,5], 3)}`); // 12
    
    // 2. Two Pointers
    const arr = [1,1,2,2,3];
    console.log(`2. Dedup length: ${DSAPatterns.removeDuplicates(arr)}`); // 3
    
    // 3. Monotonic Stack
    console.log(`3. Next greater: ${DSAPatterns.nextGreaterElement([2,1,3])}`); // [3,-1,-1]
    
    // 4. Heap
    console.log(`4. Top 2: ${DSAPatterns.topKElements([3,1,4,1,5], 2)}`);
    
    // 5. Binary Search
    console.log(`5. Lower bound of 3: ${DSAPatterns.lowerBound([1,2,3,3,4], 3)}`); // 2
    
    // 16. Union-Find
    console.log(`16. Connected components: ${DSAPatterns.countComponents(5, [[0,1],[1,2],[3,4]])}`); // 2
    
    console.log("All patterns implemented successfully!");
}

// Export for module usage
export { DSAPatterns, ListNode };

// Run demo if this is the main module
if (require.main === module) {
    main();
}