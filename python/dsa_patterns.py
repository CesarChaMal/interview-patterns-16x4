"""
16 Essential DSA Patterns for Coding Interviews - Python Implementation
Clean, readable Python with type hints and optimal time complexity
"""

from collections import deque, defaultdict
from typing import List, Optional
import heapq


class ListNode:
    def __init__(self, val: int = 0, next: Optional['ListNode'] = None):
        self.val = val
        self.next = next


class DSAPatterns:
    
    # 1. Sliding Window - max sum of subarray size k
    @staticmethod
    def max_sum_k(arr: List[int], k: int) -> int:
        window_sum = 0
        max_sum = float('-inf')
        
        for i, x in enumerate(arr):
            window_sum += x
            if i >= k:
                window_sum -= arr[i - k]
            if i >= k - 1:
                max_sum = max(max_sum, window_sum)
        
        return max_sum
    
    # 2. Two Pointers - remove duplicates from sorted array
    @staticmethod
    def remove_duplicates(nums: List[int]) -> int:
        if not nums:
            return 0
        
        write = 1
        for read in range(1, len(nums)):
            if nums[read] != nums[write - 1]:
                nums[write] = nums[read]
                write += 1
        
        return write
    
    # 3. Monotonic Stack - next greater element
    @staticmethod
    def next_greater_element(nums: List[int]) -> List[int]:
        n = len(nums)
        result = [-1] * n
        stack = []
        
        for i, x in enumerate(nums):
            while stack and x > nums[stack[-1]]:
                result[stack.pop()] = x
            stack.append(i)
        
        return result
    
    # 4. Heap (Top-K) - k largest elements
    @staticmethod
    def top_k_elements(nums: List[int], k: int) -> List[int]:
        heap = []
        
        for x in nums:
            if len(heap) < k:
                heapq.heappush(heap, x)
            elif x > heap[0]:
                heapq.heapreplace(heap, x)
        
        return heap
    
    # 5. Binary Search - lower bound (first index >= target)
    @staticmethod
    def lower_bound(nums: List[int], target: int) -> int:
        left, right = 0, len(nums)
        
        while left < right:
            mid = (left + right) // 2
            if nums[mid] < target:
                left = mid + 1
            else:
                right = mid
        
        return left
    
    # 6. Backtracking - all subsets
    @staticmethod
    def subsets(nums: List[int]) -> List[List[int]]:
        result = []
        current = []
        
        def backtrack(index: int):
            if index == len(nums):
                result.append(current.copy())
                return
            
            # Don't include current element
            backtrack(index + 1)
            
            # Include current element
            current.append(nums[index])
            backtrack(index + 1)
            current.pop()
        
        backtrack(0)
        return result
    
    # 7. BFS - shortest path in 0/1 grid
    @staticmethod
    def shortest_path_grid(grid: List[List[int]]) -> int:
        if not grid or grid[0][0] == 1:
            return -1
        
        rows, cols = len(grid), len(grid[0])
        queue = deque([(0, 0, 0)])  # (row, col, distance)
        visited = {(0, 0)}
        directions = [(1, 0), (-1, 0), (0, 1), (0, -1)]
        
        while queue:
            row, col, dist = queue.popleft()
            
            if row == rows - 1 and col == cols - 1:
                return dist
            
            for dr, dc in directions:
                nr, nc = row + dr, col + dc
                
                if (0 <= nr < rows and 0 <= nc < cols and 
                    grid[nr][nc] == 0 and (nr, nc) not in visited):
                    visited.add((nr, nc))
                    queue.append((nr, nc, dist + 1))
        
        return -1
    
    # 8. Dynamic Programming - coin change
    @staticmethod
    def coin_change(coins: List[int], amount: int) -> int:
        dp = [float('inf')] * (amount + 1)
        dp[0] = 0
        
        for coin in coins:
            for x in range(coin, amount + 1):
                dp[x] = min(dp[x], dp[x - coin] + 1)
        
        return dp[amount] if dp[amount] != float('inf') else -1
    
    # 9. Fast/Slow Pointers - cycle detection
    @staticmethod
    def detect_cycle_start(head: Optional[ListNode]) -> Optional[ListNode]:
        if not head:
            return None
        
        slow = fast = head
        
        # Find intersection point
        while fast and fast.next:
            slow = slow.next
            fast = fast.next.next
            if slow == fast:
                break
        else:
            return None  # No cycle
        
        # Find start of cycle
        slow = head
        while slow != fast:
            slow = slow.next
            fast = fast.next
        
        return slow
    
    # 10. In-place Linked List Reverse
    @staticmethod
    def reverse_linked_list(head: Optional[ListNode]) -> Optional[ListNode]:
        prev = None
        current = head
        
        while current:
            next_node = current.next
            current.next = prev
            prev = current
            current = next_node
        
        return prev
    
    # 11. Intervals - merge overlapping
    @staticmethod
    def merge_intervals(intervals: List[List[int]]) -> List[List[int]]:
        if not intervals:
            return []
        
        intervals.sort(key=lambda x: x[0])
        merged = [intervals[0]]
        
        for current in intervals[1:]:
            if merged[-1][1] >= current[0]:
                merged[-1][1] = max(merged[-1][1], current[1])
            else:
                merged.append(current)
        
        return merged
    
    # 12. Prefix Sum + HashMap - subarray sum equals k
    @staticmethod
    def subarray_sum_equals_k(nums: List[int], k: int) -> int:
        count = 0
        prefix_sum = 0
        sum_count = defaultdict(int)
        sum_count[0] = 1
        
        for num in nums:
            prefix_sum += num
            count += sum_count[prefix_sum - k]
            sum_count[prefix_sum] += 1
        
        return count
    
    # 13. Binary Search on Answer - split array minimize largest sum
    @staticmethod
    def split_array_min_max(nums: List[int], m: int) -> int:
        def can_split(max_sum: int) -> bool:
            splits = 1
            current_sum = 0
            
            for num in nums:
                if current_sum + num > max_sum:
                    splits += 1
                    current_sum = 0
                current_sum += num
            
            return splits <= m
        
        left, right = max(nums), sum(nums)
        
        while left < right:
            mid = (left + right) // 2
            if can_split(mid):
                right = mid
            else:
                left = mid + 1
        
        return left
    
    # 14. Monotonic Deque - sliding window maximum
    @staticmethod
    def max_sliding_window(nums: List[int], k: int) -> List[int]:
        dq = deque()  # stores indices
        result = []
        
        for i, num in enumerate(nums):
            # Remove indices outside window
            while dq and dq[0] <= i - k:
                dq.popleft()
            
            # Remove smaller elements from back
            while dq and nums[dq[-1]] <= num:
                dq.pop()
            
            dq.append(i)
            
            # Add to result when window is full
            if i >= k - 1:
                result.append(nums[dq[0]])
        
        return result
    
    # 15. Topological Sort - course schedule
    @staticmethod
    def can_finish_courses(num_courses: int, prerequisites: List[List[int]]) -> bool:
        graph = defaultdict(list)
        indegree = [0] * num_courses
        
        # Build graph and indegree array
        for course, prereq in prerequisites:
            graph[prereq].append(course)
            indegree[course] += 1
        
        # Start with courses having no prerequisites
        queue = deque([i for i in range(num_courses) if indegree[i] == 0])
        completed = 0
        
        while queue:
            course = queue.popleft()
            completed += 1
            
            for next_course in graph[course]:
                indegree[next_course] -= 1
                if indegree[next_course] == 0:
                    queue.append(next_course)
        
        return completed == num_courses
    
    # 16. Union-Find - connected components
    class UnionFind:
        def __init__(self, n: int):
            self.parent = list(range(n))
            self.rank = [0] * n
            self.components = n
        
        def find(self, x: int) -> int:
            if self.parent[x] != x:
                self.parent[x] = self.find(self.parent[x])
            return self.parent[x]
        
        def union(self, x: int, y: int) -> bool:
            px, py = self.find(x), self.find(y)
            if px == py:
                return False
            
            if self.rank[px] < self.rank[py]:
                px, py = py, px
            
            self.parent[py] = px
            if self.rank[px] == self.rank[py]:
                self.rank[px] += 1
            
            self.components -= 1
            return True
    
    @staticmethod
    def count_components(n: int, edges: List[List[int]]) -> int:
        uf = DSAPatterns.UnionFind(n)
        for a, b in edges:
            uf.union(a, b)
        return uf.components


def main():
    print("=== DSA Patterns Demo (Python) ===")
    
    patterns = DSAPatterns()
    
    # 1. Sliding Window
    print(f"1. Max sum k=3: {patterns.max_sum_k([1,2,3,4,5], 3)}")  # 12
    
    # 2. Two Pointers
    arr = [1,1,2,2,3]
    print(f"2. Dedup length: {patterns.remove_duplicates(arr)}")  # 3
    
    # 3. Monotonic Stack
    print(f"3. Next greater: {patterns.next_greater_element([2,1,3])}")  # [3,-1,-1]
    
    # 4. Heap
    print(f"4. Top 2: {patterns.top_k_elements([3,1,4,1,5], 2)}")
    
    # 5. Binary Search
    print(f"5. Lower bound of 3: {patterns.lower_bound([1,2,3,3,4], 3)}")  # 2
    
    # 16. Union-Find
    print(f"16. Connected components: {patterns.count_components(5, [[0,1],[1,2],[3,4]])}")  # 2
    
    print("All patterns implemented successfully!")


if __name__ == "__main__":
    main()