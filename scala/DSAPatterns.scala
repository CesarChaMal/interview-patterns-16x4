import scala.collection.mutable
import scala.util.control.Breaks._

/**
 * 15 Essential DSA Patterns for Coding Interviews - Scala Implementation
 * Idiomatic Scala with functional style where appropriate
 */
object ScalaDSAPatterns {
  
  // 1. Sliding Window - max sum of subarray size k
  def maxSumK(a: Array[Int], k: Int): Int = {
    var sum = 0
    var best = Int.MinValue
    for (i <- a.indices) {
      sum += a(i)
      if (i >= k) sum -= a(i - k)
      if (i >= k - 1) best = best max sum
    }
    best
  }
  
  // 2. Two Pointers - remove duplicates from sorted array
  def dedup(a: Array[Int]): Int = {
    if (a.isEmpty) 0
    else {
      var w = 1
      for (r <- 1 until a.length) {
        if (a(r) != a(w - 1)) {
          a(w) = a(r)
          w += 1
        }
      }
      w
    }
  }
  
  // 3. Monotonic Stack - next greater element
  def nextGreater(a: Array[Int]): Array[Int] = {
    val n = a.length
    val ans = Array.fill(n)(-1)
    val st = mutable.Stack[Int]()
    
    for (i <- 0 until n) {
      while (st.nonEmpty && a(i) > a(st.top)) {
        ans(st.pop()) = a(i)
      }
      st.push(i)
    }
    ans
  }
  
  // 4. Heap (Top-K) - k largest elements
  def topK(a: Array[Int], k: Int): List[Int] = {
    val pq = mutable.PriorityQueue[Int]()(Ordering.Int.reverse) // min heap
    a.foreach { x =>
      pq.enqueue(x)
      if (pq.size > k) pq.dequeue()
    }
    pq.toList
  }
  
  // 5. Binary Search - lower bound
  def lowerBound(a: Array[Int], t: Int): Int = {
    var l = 0
    var r = a.length
    while (l < r) {
      val m = (l + r) >>> 1
      if (a(m) < t) l = m + 1
      else r = m
    }
    l
  }
  
  // 6. Backtracking - all subsets
  def subsets(nums: Array[Int]): List[List[Int]] = {
    def go(i: Int, cur: List[Int]): List[List[Int]] = {
      if (i == nums.length) List(cur)
      else go(i + 1, cur) ::: go(i + 1, nums(i) :: cur)
    }
    go(0, Nil).map(_.reverse)
  }
  
  // 7. BFS - shortest path in 0/1 grid
  def shortest(g: Array[Array[Int]]): Int = {
    val n = g.length
    val m = g(0).length
    val vis = Array.fill(n, m)(false)
    val q = mutable.Queue((0, 0, 0))
    vis(0)(0) = true
    val dirs = Array((1, 0), (-1, 0), (0, 1), (0, -1))
    
    while (q.nonEmpty) {
      val (x, y, steps) = q.dequeue()
      if (x == n - 1 && y == m - 1) return steps
      
      for ((dx, dy) <- dirs) {
        val nx = x + dx
        val ny = y + dy
        if (0 <= nx && nx < n && 0 <= ny && ny < m && 
            g(nx)(ny) == 0 && !vis(nx)(ny)) {
          vis(nx)(ny) = true
          q.enqueue((nx, ny, steps + 1))
        }
      }
    }
    -1
  }
  
  // 8. Dynamic Programming - coin change
  def coinChange(coins: Array[Int], amount: Int): Int = {
    val INF = 1000000000
    val dp = Array.fill(amount + 1)(INF)
    dp(0) = 0
    
    for (coin <- coins; x <- coin to amount) {
      dp(x) = dp(x) min (dp(x - coin) + 1)
    }
    if (dp(amount) == INF) -1 else dp(amount)
  }
  
  // 9. Fast/Slow Pointers - cycle detection
  case class ListNode(var value: Int, var next: ListNode = null)
  
  def cycleStart(head: ListNode): ListNode = {
    if (head == null) return null
    
    var slow = head
    var fast = head
    
    // Find intersection
    breakable {
      while (fast != null && fast.next != null) {
        slow = slow.next
        fast = fast.next.next
        if (slow == fast) break
      }
    }
    
    if (fast == null || fast.next == null) return null
    
    // Find start of cycle
    slow = head
    while (slow != fast) {
      slow = slow.next
      fast = fast.next
    }
    slow
  }
  
  // 10. In-place Linked List Reverse
  def reverse(head: ListNode): ListNode = {
    var prev: ListNode = null
    var cur = head
    
    while (cur != null) {
      val nxt = cur.next
      cur.next = prev
      prev = cur
      cur = nxt
    }
    prev
  }
  
  // 11. Intervals - merge overlapping
  def merge(intervals: Array[Array[Int]]): Array[Array[Int]] = {
    val sorted = intervals.sortBy(_(0))
    val result = mutable.ArrayBuffer[Array[Int]]()
    
    for (interval <- sorted) {
      if (result.isEmpty || result.last(1) < interval(0)) {
        result += interval.clone()
      } else {
        result.last(1) = result.last(1) max interval(1)
      }
    }
    result.toArray
  }
  
  // 12. Prefix Sum + HashMap - subarray sum equals k
  def subarraySum(nums: Array[Int], k: Int): Int = {
    val map = mutable.HashMap[Int, Int](0 -> 1)
    var prefixSum = 0
    var count = 0
    
    for (x <- nums) {
      prefixSum += x
      count += map.getOrElse(prefixSum - k, 0)
      map.update(prefixSum, map.getOrElse(prefixSum, 0) + 1)
    }
    count
  }
  
  // 13. Binary Search on Answer - split array minimize largest sum
  def splitArray(nums: Array[Int], m: Int): Int = {
    var lo = nums.max
    var hi = nums.sum
    
    while (lo < hi) {
      val mid = (lo + hi) >>> 1
      var need = 1
      var cur = 0
      
      for (x <- nums) {
        if (cur + x > mid) {
          need += 1
          cur = 0
        }
        cur += x
      }
      
      if (need <= m) hi = mid
      else lo = mid + 1
    }
    lo
  }
  
  // 14. Monotonic Deque - sliding window maximum
  def maxSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    val dq = mutable.ArrayDeque[Int]()
    val n = nums.length
    val result = mutable.ArrayBuffer[Int]()
    
    for (i <- 0 until n) {
      while (dq.nonEmpty && dq.head <= i - k) dq.removeHead()
      while (dq.nonEmpty && nums(dq.last) <= nums(i)) dq.removeLast()
      dq.addLast(i)
      if (i >= k - 1) result += nums(dq.head)
    }
    result.toArray
  }
  
  // 15. Topological Sort - course schedule
  def canFinish(numCourses: Int, prerequisites: Array[Array[Int]]): Boolean = {
    val graph = Array.fill(numCourses)(mutable.ListBuffer[Int]())
    val indegree = Array.fill(numCourses)(0)
    
    for (Array(course, prereq) <- prerequisites) {
      graph(prereq) += course
      indegree(course) += 1
    }
    
    val queue = mutable.Queue[Int]()
    for (i <- 0 until numCourses if indegree(i) == 0) {
      queue.enqueue(i)
    }
    
    var completed = 0
    while (queue.nonEmpty) {
      val course = queue.dequeue()
      completed += 1
      for (next <- graph(course)) {
        indegree(next) -= 1
        if (indegree(next) == 0) queue.enqueue(next)
      }
    }
    completed == numCourses
  }
  
  // 16. Union-Find - connected components
  class UnionFind(n: Int) {
    private val parent = Array.tabulate(n)(identity)
    private val rank = Array.fill(n)(0)
    var components = n
    
    def find(x: Int): Int = {
      if (parent(x) != x) parent(x) = find(parent(x))
      parent(x)
    }
    
    def union(x: Int, y: Int): Boolean = {
      val px = find(x)
      val py = find(y)
      if (px == py) return false
      
      val (root, child) = if (rank(px) >= rank(py)) (px, py) else (py, px)
      parent(child) = root
      if (rank(px) == rank(py)) rank(root) += 1
      components -= 1
      true
    }
  }
  
  def countComponents(n: Int, edges: Array[Array[Int]]): Int = {
    val uf = new UnionFind(n)
    edges.foreach { case Array(a, b) => uf.union(a, b) }
    uf.components
  }
  
  def main(args: Array[String]): Unit = {
    println("=== DSA Patterns Demo (Scala) ===")
    
    // 1. Sliding Window
    println(s"1. Max sum k=3: ${maxSumK(Array(1,2,3,4,5), 3)}") // 12
    
    // 2. Two Pointers
    val arr = Array(1,1,2,2,3)
    println(s"2. Dedup length: ${dedup(arr)}") // 3
    
    // 3. Monotonic Stack
    println(s"3. Next greater: ${nextGreater(Array(2,1,3)).mkString("[", ",", "]")}") // [3,-1,-1]
    
    // 4. Heap
    println(s"4. Top 2: ${topK(Array(3,1,4,1,5), 2)}") 
    
    // 5. Binary Search
    println(s"5. Lower bound of 3: ${lowerBound(Array(1,2,3,3,4), 3)}") // 2
    
    // 16. Union-Find
    println(s"16. Connected components: ${countComponents(5, Array(Array(0,1), Array(1,2), Array(3,4)))}") // 2
    
    println("All patterns implemented successfully!")
  }
}